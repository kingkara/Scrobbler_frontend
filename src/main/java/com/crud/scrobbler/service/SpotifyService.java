package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.spotify.SpotifyCurrentlyPlayedDto;
import com.crud.scrobbler.domain.spotify.SpotifyFullTrackDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class SpotifyService {
    private RestTemplate restTemplate;

    public SpotifyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SpotifyFullTrackDto> getPlayback() {
        SpotifyFullTrackDto[] list = restTemplate.getForObject("http://localhost:8088/v1/spotify/playback", SpotifyFullTrackDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public SpotifyCurrentlyPlayedDto getCurrentPlaying() {
        return restTemplate.getForObject("http://localhost:8088/v1/spotify/current", SpotifyCurrentlyPlayedDto.class);
    }

}
