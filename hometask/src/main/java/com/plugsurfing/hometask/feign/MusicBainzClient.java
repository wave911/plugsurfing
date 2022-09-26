package com.plugsurfing.hometask.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@FeignClient(name = "musicbainz-client", url = "${musicbainz.apiUrl}")
public interface MusicBainzClient {

    @GetMapping(value = "/artist/{artistId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> getArtistDetails(@PathVariable(name = "artistId") String artistId,
                                                         @RequestParam(name = "fmt", defaultValue = "json") String fmt,
                                                         @RequestParam(name = "inc", required = false) String incReleaseGroups);

}
