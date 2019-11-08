package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.Comment;
import com.crud.scrobbler.domain.CommentDto;
import com.crud.scrobbler.mapper.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentsService {
    private RestTemplate restTemplate;
    private CommentsMapper mapper;

    @Autowired
    public CommentsService(RestTemplate restTemplate, CommentsMapper commentsMapper) {
        this.restTemplate = restTemplate;
        this.mapper = commentsMapper;
    }

    public CommentDto getComments(final long trackId) {
        return restTemplate.getForObject("http://localhost:8088/v1/comments/" + trackId, CommentDto.class);
    }

    public CommentDto addComment(final Comment comment) {
        CommentDto commentDto = mapper.mapToCommentDto(comment);
        return restTemplate.postForObject("http://localhost:8088/v1/comments", commentDto, CommentDto.class);
    }

    public void editComment(final CommentDto commentDto) {
        restTemplate.put("http://localhost:8088/v1/comments", commentDto, CommentDto.class);
    }

    public void deleteComment(final long id) {
        restTemplate.delete("http://localhost:8088/v1/comments/" + id);
    }
}
