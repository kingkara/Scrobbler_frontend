package com.crud.scrobbler.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("text")
    private String text;
    @JsonProperty("user")
    private User user;
    @JsonProperty("track")
    private Track track;
}
