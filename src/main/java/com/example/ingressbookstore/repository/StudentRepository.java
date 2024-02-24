package com.example.ingressbookstore.repository;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.entity.BookEntity;
import com.example.ingressbookstore.entity.StudentEntity;
import com.example.ingressbookstore.model.request.SaveStudentSubscriptionRequest;
import com.example.ingressbookstore.model.response.StudentSubscribeAuthorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    @Query(value = "select  readbook.name from StudentEntity s join s.readBooks readbook where s.id=:id")
    List<String> findReadBooksById(Long id);
    @Query(value = "select subscribe.name,subscribe.surname  from StudentEntity  a join a.subscription subscribe where a.id=:id")
    List<NameAndSurnameProjection>findSubscriptionById(Long id);


}


