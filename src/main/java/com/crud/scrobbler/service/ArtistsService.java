package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.ArtistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ArtistsService {
    private RestTemplate restTemplate;

    @Autowired
    public ArtistsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ArtistDto getArtistByName(final String name) {
        return restTemplate.getForObject("http:localhost:8088/v1/artists/" + name, ArtistDto.class);
    }
}
