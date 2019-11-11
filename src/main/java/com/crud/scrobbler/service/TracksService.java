package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.TrackDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TracksService {
    private RestTemplate restTemplate;

    public TracksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TrackDto getTrack(final String title) {
        return restTemplate.getForObject("http://localhost:8088/v1/tracks/" + title, TrackDto.class);
    }

    public List<TrackDto> getTracks() {
        TrackDto[] list = restTemplate.getForObject("http://localhost:8088/v1/tracks", TrackDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public List<TrackDto> findByTitle(String title) {
        List<TrackDto> tracks = getTracks();
        return tracks.stream().filter(track -> track.getTitle().contains(title)).collect(Collectors.toList());
    }

    public List<TrackDto> findByArtistName(String artistName) {
        TrackDto[] list = restTemplate.getForObject("http://localhost:8088/v1/tracks/artist/" + artistName, TrackDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }
}
