package ru.svetlov.webstore.api.dto;

import java.util.Collection;

public class CommentPageDto {
    private Collection<CommentDto> comments;
    private boolean canAddComment;

    public CommentPageDto() {
    }

    public CommentPageDto(Collection<CommentDto> comments, boolean canAddComment) {
        this.comments = comments;
        this.canAddComment = canAddComment;
    }

    public Collection<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Collection<CommentDto> comments) {
        this.comments = comments;
    }

    public boolean isCanAddComment() {
        return canAddComment;
    }

    public void setCanAddComment(boolean canAddComment) {
        this.canAddComment = canAddComment;
    }
}
