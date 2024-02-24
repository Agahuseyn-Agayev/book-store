package com.example.ingressbookstore.repository;

import com.example.ingressbookstore.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    @Query(value = "select book.name from BookEntity book where book.author.id=:id")
   List<String> findBookNameByAuthorId(Long id);

    @Query(value = "SELECT ss.name as name,ss.email as email FROM AuthorEntity  a join  a.subscribedStudents  ss  WHERE a.id=:id")
    List<NameAndEmailProjection> findSubscribedStudentsById(Long id);
}
