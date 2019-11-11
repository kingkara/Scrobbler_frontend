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
public class CommentDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("trackTitle")
    private String trackTitle;

    public CommentDto(String text, String userName, String title) {
        this.text = text;
        this.userName = userName;
        this.trackTitle = title;
    }
}
