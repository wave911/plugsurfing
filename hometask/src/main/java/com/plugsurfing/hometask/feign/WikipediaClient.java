package com.plugsurfing.hometask.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "wikipedia-client", url = "${wikipedia.apiUrl}")
public interface WikipediaClient {
    @GetMapping(value = "/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> getArtistDetails(@PathVariable(name = "title") String title);
}
