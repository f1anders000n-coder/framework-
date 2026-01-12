package com.example.complexlab.persistence.service;

import com.example.complexlab.core.model.AppUser;
import com.example.complexlab.core.model.Book;
import com.example.complexlab.core.model.Comment;
import com.example.complexlab.core.service.CommentService;
import com.example.complexlab.persistence.repository.BookRepository;
import com.example.complexlab.persistence.repository.CommentRepository;
import com.example.complexlab.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> findByBookId(Long bookId) {
        return commentRepository.findByBookIdWithAuthor(bookId);
    }

    @Override
    @Transactional
    public Comment addComment(Long bookId, String username, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        AppUser author = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        return commentRepository.save(new Comment(book, author, text, Instant.now()));
    }

    @Override
    @Transactional
    public void delete(Long commentId, String username, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        String authorUsername = comment.getAuthor().getUsername();
        if (!isAdmin && !authorUsername.equals(username)) {
            throw new AccessDeniedException("Not allowed");
        }
        commentRepository.delete(comment);
    }
}
