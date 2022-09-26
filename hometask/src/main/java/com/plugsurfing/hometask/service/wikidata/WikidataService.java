package com.plugsurfing.hometask.service.wikidata;

import com.plugsurfing.hometask.feign.WikidataClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class WikidataService {
    private final WikidataClient wikidataClient;

    public Map<String, Object> getArtistDetails(String wikidataId) {
        ResponseEntity<Map<String, Object>> response = null;
        try {
            response = wikidataClient.getArtistDetails(wikidataId);
        }
        catch (FeignException e) {
            log.error("FeignException while getting WikiData details for {} msg: {}", wikidataId, e.getMessage());
        }
        if (response == null) {
            log.error("WikidataService returned null response for artistId {}", wikidataId);
            return null;
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("WikidataService failed to get data for artistId {} status {} ", response.getStatusCode(), wikidataId);
            return null;
        }
        log.info("WikidataService returned {} for artistId {}", response.getStatusCode(), wikidataId);
        return response.getBody();
    }

    public String getWikipediaTitle(Map<String, Object> wikiData, String id) {
        Optional<String> title = Optional.ofNullable(getNestedValue(wikiData, "entities", id, "sitelinks", "enwiki", "title"));
        if (title.isPresent())
            return title.get();
        return null;
    }

    // https://stackoverflow.com/questions/2774608/how-do-i-access-nested-hashmaps-in-java
    private <T> T getNestedValue(Map map, String... keys) {
        Object value = map;
        for (String key : keys) {
            value = ((Map) value).get(key);
        }
        return (T) value;
    }
}
