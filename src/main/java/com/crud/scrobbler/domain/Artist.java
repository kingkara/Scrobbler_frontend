package com.crud.scrobbler.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Artist {

    public Artist(String name, String spotifyArtistId) {
        this.name = name;
        this.spotifyArtistId = spotifyArtistId;
    }

    protected long artistId;
    private String name;
    private String spotifyArtistId;
    private List<UsersArtist> usersArtist = new ArrayList<>();
    private List<Track> tracks = new ArrayList<>();
}
