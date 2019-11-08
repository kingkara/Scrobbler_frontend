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
public class UsersTrackDto {
    @JsonProperty("id")
    private UsersTrack.UsersTrackIdBuilder id;
    @JsonProperty("lastPlayedTime")
    private String lastPlayedTime;
    @JsonProperty("count")
    private long count;
    @JsonProperty("favouriteStatus")
    private boolean favouriteStatus;
    @JsonProperty("user")
    private User user;
    @JsonProperty("track")
    private Track track;
}
