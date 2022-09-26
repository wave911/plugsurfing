package com.plugsurfing.hometask.service.coverartarchive;

import com.plugsurfing.hometask.entity.CoverArt;
import com.plugsurfing.hometask.feign.CoverArtArchiveClient;
import com.plugsurfing.hometask.repository.CoverArtRepository;
import com.plugsurfing.hometask.utils.JsonConverter;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CoverArtArchiveService {
    private final CoverArtArchiveClient coverArtArchiveClient;
    private final CoverArtRepository coverArtRepository;
    JsonConverter jsonConverter = new JsonConverter();

    public Map<String, Object> getCoverDetails(String albumCoverId) {
      Optional<CoverArt> data = coverArtRepository.findById(albumCoverId);
      if (data.isPresent()) {
        log.info("CoverArtArchiveService retrieved data from cache for {}", albumCoverId);
        return jsonConverter.convertToEntityAttribute(data.get().getValue());//data.get().getData();
      }

      ResponseEntity<Map<String, Object>> response = null;
      try {
          response = coverArtArchiveClient.getArtistDetails(albumCoverId);
      }
      catch (FeignException e) {
          log.error("FeignException while getting cover images URLs for {} msg: {}", albumCoverId, e.getMessage());
      }
      if (response == null) {
          log.error("CoverArtArchiveService returned null response for artistId {}", albumCoverId);
          return null;
      }
      if (response.getStatusCode() != HttpStatus.OK) {
          log.error("CoverArtArchiveService failed to get data for artistId {} status {} ", response.getStatusCode(), albumCoverId);
          return null;
      }
      log.info("CoverArtArchiveService returned {} for artistId {}", response.getStatusCode(), albumCoverId);

      CoverArt coverArt = new CoverArt(albumCoverId, jsonConverter.convertToDatabaseColumn(response.getBody()));//response.getBody());
      coverArtRepository.save(coverArt);
      return response.getBody();
    }


    public String getCoverUrlByMusicBainzId(String id) {
        if ((id == null) || (id.isEmpty()))
            return null;
        Optional<Map<String, Object>> ids = Optional.ofNullable(getCoverDetails(id));
        if (ids.isPresent()) {
            Optional<List<Map<String, Object>>> images = Optional.ofNullable((List<Map<String, Object>>)ids.get().get("images"));
            if (images.isPresent()) {
                return (String)images.get().get(0).getOrDefault("image", null);
            }
        }
        return null;
    }
}
