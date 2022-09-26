package com.plugsurfing.hometask.service.wikipedia;

import com.plugsurfing.hometask.feign.WikipediaClient;
import feign.Feign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class WikipediaService {
    private final WikipediaClient wikipediaClient;

    public Map<String, Object> getArtistDetails(String wikipediaTitle) {
        ResponseEntity<Map<String, Object>> response = null;
        try {
            response = wikipediaClient.getArtistDetails(wikipediaTitle);
        }
        catch (FeignException e) {
            log.error("FeignException while getting Wikipedia details for {} msg: {}", wikipediaTitle, e.getMessage());
        }
        if (response == null) {
            log.error("WikipediaService returned null response for artistId {}", wikipediaTitle);
            return null;
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("WikipediaService failed to get data for artistId {} status {} ", response.getStatusCode(), wikipediaTitle);
            return null;
        }
        log.info("WikipediaService returned {} for artistId {}", response.getStatusCode(), wikipediaTitle);
        return response.getBody();
    }
}
