package com.crud.scrobbler.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class Comment {

    public Comment(String text, User user, Track track) {
        this.text = text;
        this.user = user;
        this.track = track;
    }

    private long id;
    private String text;
    private User user;
    private Track track;
}
