package com.crud.scrobbler.mapper;

import com.crud.scrobbler.domain.Comment;
import com.crud.scrobbler.domain.CommentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentsMapper {

    public Comment mapToComment(final CommentDto commentDto) {
        return new Comment(commentDto.getText(), commentDto.getUser(), commentDto.getTrack());
    }

    public CommentDto mapToCommentDto(final Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getUser(), comment.getTrack());
    }

    public List<CommentDto> mapToCommentDtoList(final List<Comment> comments) {
        return comments.stream()
                .map(c -> new CommentDto(c.getId(), c.getText(), c.getUser(), c.getTrack()))
                .collect(Collectors.toList());
    }
}
