package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommentsService {
    private RestTemplate restTemplate;

    @Autowired
    public CommentsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CommentDto> getComments(final long trackId) {
        CommentDto[] list = restTemplate.getForObject("http://localhost:8088/v1/comments/" + trackId, CommentDto[].class);
        if (list != null) {
            return Arrays.asList(list);
        }
        return new ArrayList<>();
    }

    public void addComment(final CommentDto commentDto) {
        restTemplate.postForObject("http://localhost:8088/v1/comments", commentDto, CommentDto.class);
    }

    public void editComment(final CommentDto commentDto) {
        restTemplate.put("http://localhost:8088/v1/comments", commentDto, CommentDto.class);
    }

    public void deleteComment(final long id) {
        restTemplate.delete("http://localhost:8088/v1/comments/" + id);
    }
}
