package com.example.complexlab.core.service;

import com.example.complexlab.core.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book create(String title, String author, String description);
    Book update(Long id, String title, String author, String description);
    void delete(Long id);
}
