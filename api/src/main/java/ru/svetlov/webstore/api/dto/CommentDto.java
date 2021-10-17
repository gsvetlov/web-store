package ru.svetlov.webstore.api.dto;

import java.time.LocalDateTime;

public class CommentDto {
    private String content;
    private String user;
    private LocalDateTime date;

    public CommentDto() {
    }

    public CommentDto(String content, String user, LocalDateTime date) {
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
