package com.example.complexlab.web.controller;

import com.example.complexlab.core.model.Book;
import com.example.complexlab.core.service.BookService;
import com.example.complexlab.core.service.CommentService;
import jakarta.validation.constraints.NotBlank;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@Validated
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CommentService commentService;

    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new BookForm());
        return "book/book-form";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id).orElseThrow();
        model.addAttribute("book", book);
        model.addAttribute("comments", commentService.findByBookId(id));
        model.addAttribute("commentText", "");
        return "book/details";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public String create(
        @RequestParam @NotBlank String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String description
    ) {
        bookService.create(title, author, description);
        return "redirect:/";
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam @NotBlank String commentText, Authentication authentication) {
        commentService.addComment(id, authentication.getName(), commentText);
        return "redirect:/books/" + id;
    }
}
