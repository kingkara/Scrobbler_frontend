package com.crud.scrobbler.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class UsersArtist {
    public static class UsersArtistIdBuilder implements Serializable {
        protected Long userId;
        private Long artistId;

        UsersArtistIdBuilder() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UsersArtistIdBuilder)) return false;

            UsersArtistIdBuilder id = (UsersArtistIdBuilder) o;

            if (!userId.equals(id.userId)) return false;
            return artistId.equals(id.artistId);
        }

        @Override
        public int hashCode() {
            return userId.hashCode() + artistId.hashCode();
        }

        @Override
        public String toString() {
            return userId.toString() + artistId.toString();
        }
    }

    public UsersArtist(User user, Artist artist) {
        this.user = user;
        this.artist = artist;
        this.id.userId = user.getId();
        this.id.artistId = artist.getArtistId();
        this.count = 1;
    }

    private UsersArtistIdBuilder id = new UsersArtistIdBuilder();

    private long count;

    private String lastPlayedTime;

    private User user;

    private Artist artist;
}
