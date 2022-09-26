package com.plugsurfing.hometask.service.musicbrainz;

import com.plugsurfing.hometask.feign.MusicBainzClient;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MusicBainzService {

    private final MusicBainzClient musicBainzClient;

    public Map<String, Object> getArtistDetails(String mbId) {
        ResponseEntity<Map<String, Object>> response = null;
        try {
            response = musicBainzClient.getArtistDetails(mbId, "json", "url-rels+release-groups");
        }
        catch (FeignException e) {
            log.error("FeignException while getting MusiBainz details for {} msg: {}", mbId, e.getMessage());
        }
        if (response == null) {
            log.error("MusicBainzService returned null response for artistId {}", mbId);
            return null;
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("MusicBainzService failed to get data for artistId {} status {} ", response.getStatusCode(), mbId);
            return null;
        }
        log.info("MusicBainzService returned {} for artistId {}", response.getStatusCode(), mbId);
        return response.getBody();
    }

    public Optional<String> getWikidataLink(Map<String, Object> mdData) {
        Optional<List<Map<String, Object>>> urlList = getUrlList(mdData);
        return urlList.map(this::getLink);
    }

    public String getWikiDataId(String wikidataLink) {
        if (wikidataLink == null)
            return null;
        String [] parts = wikidataLink.split("/");
        return parts[parts.length - 1];
    }

    protected Optional<List<Map<String, Object>>> getUrlList(Map<String, Object> mdData) {
        Optional<List<Map<String, Object>>> relations = Optional.of((List<Map<String, Object>>)mdData.get("relations"));
        if (relations.isPresent()) {
            List<Map<String, Object>> urlList = relations.get().stream().filter(t -> {
                if (t.containsKey("type") && (t.get("type").equals("wikidata"))) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            return Optional.of(urlList);
        }
        return Optional.empty();
    }

    protected String getLink(List<Map<String, Object>> urlList) {
        if (urlList.size() == 0)
            return null;
        Map<String, Object> wikiDataUrl = (Map<String, Object>)urlList.get(0).get("url");
        return String.valueOf(wikiDataUrl.getOrDefault("resource", null));
    }
}
