package com.example.complexlab.web.api;

import com.example.complexlab.core.dto.BookDto;
import com.example.complexlab.core.model.Book;
import com.example.complexlab.core.service.BookService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@PreAuthorize("isAuthenticated()")
public class BookApiController {

    private final BookService bookService;

    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> all() {
        return bookService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public BookDto byId(@PathVariable Long id) {
        return bookService.findById(id).map(this::toDto).orElseThrow();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestParam @NotBlank String title,
                          @RequestParam(required = false) String author,
                          @RequestParam(required = false) String description) {
        return toDto(bookService.create(title, author, description));
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id,
                          @RequestParam @NotBlank String title,
                          @RequestParam(required = false) String author,
                          @RequestParam(required = false) String description) {
        return toDto(bookService.update(id, title, author, description));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)   
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    private BookDto toDto(Book b) {
        return new BookDto(b.getId(), b.getTitle(), b.getAuthor(), b.getDescription(), b.getCreatedAt());
    }
}
