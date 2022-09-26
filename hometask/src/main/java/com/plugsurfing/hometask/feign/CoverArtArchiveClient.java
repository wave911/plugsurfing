package com.plugsurfing.hometask.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "coverartarchive-client", url = "${coverartarchive.apiUrl}", decode404 = true)
public interface CoverArtArchiveClient {
    @GetMapping(value = "/release-group/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> getArtistDetails(@PathVariable(name = "id") String id);
    //ResponseEntity<Object> getArtistDetails(@PathVariable(name = "id") String id);
}
