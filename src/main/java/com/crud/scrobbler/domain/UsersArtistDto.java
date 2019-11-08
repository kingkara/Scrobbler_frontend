package com.crud.scrobbler.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersArtistDto {
    @JsonProperty("id")
    private UsersArtist.UsersArtistIdBuilder id;
    @JsonProperty("count")
    private long count;
    @JsonProperty("lastPlayedTime")
    private String lastPlayedTime;
    @JsonProperty("user")
    private User user;
    @JsonProperty("artist")
    private Artist artist;
}
