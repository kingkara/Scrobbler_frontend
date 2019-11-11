package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.ArtistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistsService {
    private RestTemplate restTemplate;

    @Autowired
    public ArtistsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ArtistDto getArtistByName(final String name) {
        return restTemplate.getForObject("http://localhost:8088/v1/artists/" + name, ArtistDto.class);
    }

    public List<ArtistDto> getArtists() {
        ArtistDto[] list = restTemplate.getForObject("http://localhost:8088/v1/artists/", ArtistDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public List<ArtistDto> findByName(String name) {
        List<ArtistDto> artists = getArtists();
        return artists.stream().filter(artist -> artist.getName().contains(name)).collect(Collectors.toList());
    }
}
