package com.example.complexlab.web.controller;

import com.example.complexlab.core.model.Book;
import com.example.complexlab.core.service.BookService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@RequestMapping("/admin/books")
public class AdminBookController {

    private final BookService bookService;

    public AdminBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new Book("", "", "", null));
        return "admin/book-form";
    }

    @PostMapping
    public String create(@RequestParam @NotBlank String title,
                         @RequestParam(required = false) String author,
                         @RequestParam(required = false) String description) {
        Book book = bookService.create(title, author, description);
        return "redirect:/books/" + book.getId();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "admin/book-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam @NotBlank String title,
                         @RequestParam(required = false) String author,
                         @RequestParam(required = false) String description) {
        bookService.update(id, title, author, description);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/";
    }
}
