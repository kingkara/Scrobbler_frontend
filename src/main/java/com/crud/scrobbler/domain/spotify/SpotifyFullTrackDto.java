package com.crud.scrobbler.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyFullTrackDto {
    @JsonProperty("track")
    private SpotifyTrackDto spotifyTrackDto;

    @JsonProperty("played_at")
    private String playedAt;
}
