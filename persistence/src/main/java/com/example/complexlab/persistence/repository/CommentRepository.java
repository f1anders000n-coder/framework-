package com.example.complexlab.persistence.repository;

import com.example.complexlab.core.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.author where c.book.id = :bookId order by c.createdAt desc")
    List<Comment> findByBookIdWithAuthor(Long bookId);
}
