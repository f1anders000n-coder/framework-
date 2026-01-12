package com.example.complexlab.persistence.repository;

import com.example.complexlab.core.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
