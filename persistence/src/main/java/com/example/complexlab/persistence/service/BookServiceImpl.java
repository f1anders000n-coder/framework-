package com.example.complexlab.persistence.service;

import com.example.complexlab.core.model.Book;
import com.example.complexlab.core.service.BookService;
import com.example.complexlab.persistence.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public Book create(String title, String author, String description) {
        return bookRepository.save(new Book(title, author, description, Instant.now()));
    }

    @Override
    @Transactional
    public Book update(Long id, String title, String author, String description) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
