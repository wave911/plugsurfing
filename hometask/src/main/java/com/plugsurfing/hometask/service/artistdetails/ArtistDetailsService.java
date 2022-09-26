package com.plugsurfing.hometask.service.artistdetails;

import com.plugsurfing.hometask.service.coverartarchive.CoverArtArchiveService;
import com.plugsurfing.hometask.service.musicbrainz.MusicBainzService;
import com.plugsurfing.hometask.service.wikidata.WikidataService;
import com.plugsurfing.hometask.service.wikipedia.WikipediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ArtistDetailsService {

    private final MusicBainzService musicBainzService;
    private final WikidataService wikidataService;
    private final WikipediaService wikipediaService;
    private final CoverArtArchiveService coverArtArchiveService;

    private ArtistDetails.Builder artistDetailsBuilder = new ArtistDetails.Builder();

    @Async
    public String getArtistDetails(String mbId) {
        //return "not implemented yet";
        log.info("Requesting data for {}", mbId);
        Map<String, Object> mbDetails = musicBainzService.getArtistDetails(mbId);
        String wikidataLink = musicBainzService.getWikidataLink(mbDetails  ).orElse(null);
        String wikiId = musicBainzService.getWikiDataId(wikidataLink);

        Map<String, Object> wikiDetails = wikidataService.getArtistDetails(wikiId);
        String title = wikidataService.getWikipediaTitle(wikiDetails, wikiId);
        Map<String, Object> wikipediaData = wikipediaService.getArtistDetails(title);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("mbid", mbDetails.get("id"));
        res.put("name", mbDetails.get("name"));
        res.put("gender", mbDetails.get("gender"));
        res.put("country", mbDetails.get("country"));
        res.put("disambiguation", mbDetails.get("disambiguation"));
        res.put("description", wikipediaData.get("extract"));
        res.put("albums", getAlbums(mbDetails));
        return res.toString();
    }

    protected List<Map<String, String>> getAlbums(Map<String, Object> mbDetails) {
        if (!mbDetails.containsKey("release-groups"))
            return null;
        List<Map<String, Object>> releaseGroups = (List<Map<String, Object>>)mbDetails.get("release-groups");
        return releaseGroups.stream().map(t -> {
                Map<String, String> item = new LinkedHashMap<>();
                String id = String.valueOf(t.getOrDefault("id", null));
                item.put("id", id);
                item.put("title", String.valueOf(t.getOrDefault("title", null)));
                item.put("imageUrl", coverArtArchiveService.getCoverUrlByMusicBainzId(id));
                return item;
            }).collect(Collectors.toList());
    }
}
