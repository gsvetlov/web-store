package ru.svetlov.webstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.svetlov.webstore.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private String content;
    private String user;
    private LocalDateTime date;

    public CommentDto(Comment comment) {
        this.content = comment.getContent();
        this.user = comment.getUser().getUsername();
        this.date = comment.getModified();
    }
}
