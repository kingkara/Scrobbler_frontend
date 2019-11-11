package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.UsersTrackDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersTracksService {
    private RestTemplate restTemplate;

    public UsersTracksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UsersTrackDto> getUsersTracks(final long userId) {
        UsersTrackDto[] list = restTemplate.getForObject("http://localhost:8088/v1/usersTracks/" + userId, UsersTrackDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public void changeFavouriteStatus(final long userId, final long trackId) {
        restTemplate.put("http://localhost:8088/v1/usersTracks/" + userId + "/" + trackId, UsersTrackDto[].class);
    }

    public List<UsersTrackDto> getTopUsersTracks(final long userId) {
        UsersTrackDto[] list = restTemplate.getForObject("http://localhost:8088/v1/usersTracks/top/" + userId, UsersTrackDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public void deleteTrack(final long userId, final long trackId) {
        restTemplate.delete("http://localhost:8088/v1/usersTracks/" + userId + "/" + trackId);
    }

}
