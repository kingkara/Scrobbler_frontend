package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.TrackDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TracksService {
    private RestTemplate restTemplate;

    public TracksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TrackDto getTrack(final String title) {
        return restTemplate.getForObject("http://localhost:8088/v1/tracks/" + title, TrackDto.class);
    }
}
