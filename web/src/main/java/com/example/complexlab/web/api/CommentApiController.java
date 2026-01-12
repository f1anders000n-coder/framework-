package com.example.complexlab.web.api;

import com.example.complexlab.core.dto.CommentDto;
import com.example.complexlab.core.model.Comment;
import com.example.complexlab.core.model.Role;
import com.example.complexlab.core.service.CommentService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/books/{bookId}/comments")
    public List<CommentDto> list(@PathVariable Long bookId) {
        return commentService.findByBookId(bookId).stream().map(this::toDto).toList();
    }

    @PostMapping("/books/{bookId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto add(@PathVariable Long bookId, @RequestParam @NotBlank String text, Authentication authentication) {
        return toDto(commentService.addComment(bookId, authentication.getName(), text));
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long commentId, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + Role.ADMIN.name()));
        commentService.delete(commentId, authentication.getName(), isAdmin);
    }

    private CommentDto toDto(Comment c) {
        return new CommentDto(c.getId(), c.getBook().getId(), c.getAuthor().getUsername(), c.getText(), c.getCreatedAt());
    }
}
