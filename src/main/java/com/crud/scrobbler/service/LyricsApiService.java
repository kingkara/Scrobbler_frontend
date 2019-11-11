package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.lyricsApi.LyricsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LyricsApiService {
    private RestTemplate restTemplate;

    public LyricsApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LyricsDto getLyrics(String artistName, String title) {
        return restTemplate.getForObject("http://localhost:8088/v1/lyrics/" + artistName.replace(" ", "%20") +"/" +
                title.replace(" ", "%20"), LyricsDto.class);
    }
}
