package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.UsersArtistDto;
import com.crud.scrobbler.domain.UsersTrackDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersArtistsService {
    private RestTemplate restTemplate;

    public UsersArtistsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UsersArtistDto> getUsersArtists(final long userId) {
        UsersArtistDto[] list = restTemplate.getForObject("http://localhost:8088/v1/usersArtists/" + userId, UsersArtistDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public List<UsersArtistDto> getTopArtists(final long userId) {
        UsersArtistDto[] list = restTemplate.getForObject("http://localhost:8088/v1/usersTopArtists/" + userId, UsersArtistDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public void deleteUsersArtist(final long artistId, final long userId) {
        restTemplate.getForObject("http://localhost:8088/v1/usersArtists/" + artistId + "/" + userId, UsersArtistDto[].class);
    }
}
