package com.example.ingressbookstore.service;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.entity.BookEntity;
import com.example.ingressbookstore.entity.StudentEntity;
import com.example.ingressbookstore.exception.NotFoundException;
import com.example.ingressbookstore.mapper.StudentMapper;
import com.example.ingressbookstore.model.response.StudentReadBookResponse;
import com.example.ingressbookstore.model.enums.Status;
import com.example.ingressbookstore.model.request.SaveStudentReadBookRequest;
import com.example.ingressbookstore.model.request.SaveStudentRequest;
import com.example.ingressbookstore.model.request.SaveStudentSubscriptionRequest;
import com.example.ingressbookstore.model.request.UpdateStudentRequest;
import com.example.ingressbookstore.model.response.StudentResponse;
import com.example.ingressbookstore.model.response.StudentSubscribeAuthorResponse;
import com.example.ingressbookstore.repository.AuthorRepository;
import com.example.ingressbookstore.repository.BookRepository;
import com.example.ingressbookstore.repository.NameAndSurnameProjection;
import com.example.ingressbookstore.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.ingressbookstore.mapper.StudentMapper.builderStudentEntity;
import static com.example.ingressbookstore.mapper.StudentMapper.builderStudentResponse;
import static com.example.ingressbookstore.model.enums.ExceptionMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentService {
    final StudentRepository repository;
    final BookRepository bookRepository;

    final AuthorRepository authorRepository;

    public StudentResponse getStudentById(Long id) {
        var student = fetchStudentIdExits(id);
        return builderStudentResponse(student);
    }

    public void saveStudent(SaveStudentRequest request) {
        repository.save(builderStudentEntity(request));
    }

    public void updateStudent(Long id, UpdateStudentRequest request) {
        var student = fetchStudentIdExits(id);
        StudentMapper.updateStudent(student, request);
        repository.save(student);
    }
    public void saveStudentReadBook(SaveStudentReadBookRequest request){
        var student=fetchStudentIdExits(request.getStudentId());
        var book=fetchBookIfExits(request.getBookId());
        student.getReadBooks().add(book);
        repository.save(student);
    }

    public void saveStudentSubscription(SaveStudentSubscriptionRequest request){
        var student=fetchStudentIdExits(request.getStudentId());
        var author=fetchAuthorIfExits(request.getAuthorId());
        student.getSubscription().add(author);
        repository.save(student);
    }

    public void deleteStudent(Long id) {
        var student = fetchStudentIdExits(id);
        student.setStatus(Status.DELETED);
        repository.save(student);
    }

    public List<StudentResponse> getStudents(){
        return repository.findAll().stream()
                .map(StudentMapper::getStudents).collect(Collectors.toList());
    }

    private StudentEntity fetchStudentIdExits(Long id) {
       return repository.findById(id).orElseThrow(
                () -> new NotFoundException(STUDENT_NOT_FOUND.getMessage())
        );
    }

    private BookEntity fetchBookIfExits(Long bookId){
        return bookRepository.findById(bookId).orElseThrow(
                ()->new NotFoundException(BOOK_NOT_FOUND.getMessage())
        );
    }
    private AuthorEntity fetchAuthorIfExits(Long authorId){
        return authorRepository.findById(authorId).orElseThrow(
                ()->new NotFoundException(AUTHOR_NOT_FOUND.getMessage())
        );
    }

    public List<StudentReadBookResponse> getReadBookByStudentId(Long id) {
        return repository.findReadBooksById(id)
                .stream()
                .map(StudentReadBookResponse::new)
                .toList();
    }
    public List<StudentSubscribeAuthorResponse> getStudentSubscribesById(Long id){
//        Optional.of(id)
//                .map(repository::findSubscriptionById)
//                .stream()
//                .flatMap(Collection::stream)
//                .map(StudentService::createResponse)
//                .collect(Collectors.toList());

        return repository.findSubscriptionById(id)
                .stream()
                .map(StudentService::createResponse)
                .collect(Collectors.toList());

    }

    private static StudentSubscribeAuthorResponse createResponse(NameAndSurnameProjection pro) {
        return new StudentSubscribeAuthorResponse(pro.getName(), pro.getSurname());
    }
}
