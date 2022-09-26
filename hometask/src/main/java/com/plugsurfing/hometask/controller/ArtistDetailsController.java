package com.plugsurfing.hometask.controller;

import com.plugsurfing.hometask.service.artistdetails.ArtistDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class ArtistDetailsController {
    private final ArtistDetailsService artistDetailsService;

    @Autowired
    public ArtistDetailsController(ArtistDetailsService artistDetailsService) {
        this.artistDetailsService = artistDetailsService;
    }

    @GetMapping(value = "/musify/music-artist/details/{mbid}")
    public String getArtistDetails(@PathVariable(name = "mbid") String mbid) throws Throwable {
        //return artistDetailsService.getArtistDetails(mbid);
        String musicDetails = null;
        String res = artistDetailsService.getArtistDetails(mbid);
        try {
            musicDetails = res;
        }
        catch (Throwable e) {
            throw e.getCause();
        }
        return musicDetails;
    }

}
