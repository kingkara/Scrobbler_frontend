package com.crud.scrobbler.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Track {

    public Track(String title, Artist artist) {
        this.title = title;
        this.artist = artist;
    }

    private long id;
    private String title;
    private Artist artist;
    private List<UsersTrack> usersTracks = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
