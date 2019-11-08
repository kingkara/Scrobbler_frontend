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
public class SpotifyCurrentlyPlayedDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String title;

    @JsonProperty("artists")
    private List<SpotifyArtistDto> artistDtos;
}
