package com.example.ingressbookstore.repository;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity,Long> {

    Optional<BookEntity> findByAuthorId(Long authorId);
}
