package com.crud.scrobbler.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor

public class UsersTrack {

    public static class UsersTrackIdBuilder implements Serializable {
        protected Long userId;
        private Long trackId;

        UsersTrackIdBuilder() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UsersTrackIdBuilder)) return false;

            UsersTrackIdBuilder id = (UsersTrackIdBuilder) o;

            if (!Objects.equals(userId, id.userId)) return false;
            return Objects.equals(trackId, id.trackId);
        }

        @Override
        public int hashCode() {
            return userId.hashCode() + trackId.hashCode();
        }
    }

    public UsersTrack(User user, Track track) {
        this.user = user;
        this.track = track;
        this.id.trackId = track.getId();
        this.id.userId = user.getId();
        this.count = 1;
        this.favouriteStatus = false;

    }

    private UsersTrackIdBuilder id = new UsersTrackIdBuilder();

    private String lastPlayedTime;

    private long count;

    private boolean favouriteStatus;

    private User user;

    private Track track;
}
