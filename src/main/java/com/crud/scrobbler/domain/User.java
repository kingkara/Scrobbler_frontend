package com.crud.scrobbler.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public User(String username, String email, String spotifyId) {
        this.username = username;
        this.email = email;
        this.spotifyId = spotifyId;
    }

    protected long id;

    private String username;

    private String email;

    private String spotifyId;

    private List<UsersArtist> usersArtists = new ArrayList<>();

    private List<UsersTrack> usersTracks = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    public boolean isNewUser() {
        return getId() == -1;
    }
}
