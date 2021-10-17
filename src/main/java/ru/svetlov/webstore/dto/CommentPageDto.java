package ru.svetlov.webstore.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
public class CommentPageDto {
    private final Collection<CommentDto> comments;
    private final boolean canAddComment;
}
