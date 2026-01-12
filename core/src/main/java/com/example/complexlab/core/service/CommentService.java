package com.example.complexlab.core.service;

import com.example.complexlab.core.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByBookId(Long bookId);
    Comment addComment(Long bookId, String username, String text);
    void delete(Long commentId, String username, boolean isAdmin);
}
