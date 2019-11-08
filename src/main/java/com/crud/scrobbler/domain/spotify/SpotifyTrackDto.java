package com.crud.scrobbler.domain.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrackDto {
    @JsonProperty("artists")
    private List<SpotifyArtistDto> spotifyArtistDto;

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String title;
}
