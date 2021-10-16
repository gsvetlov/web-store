package ru.svetlov.webstore.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class CommentPageDto {
    private final boolean canEditComment;
    private final Page<CommentDto> commentsPage;
}
