package com.example.ingressbookstore.controller;

import com.example.ingressbookstore.model.response.StudentReadBookResponse;
import com.example.ingressbookstore.model.request.SaveStudentReadBookRequest;
import com.example.ingressbookstore.model.request.SaveStudentRequest;
import com.example.ingressbookstore.model.request.SaveStudentSubscriptionRequest;
import com.example.ingressbookstore.model.request.UpdateStudentRequest;
import com.example.ingressbookstore.model.response.StudentResponse;
import com.example.ingressbookstore.model.response.StudentSubscribeAuthorResponse;
import com.example.ingressbookstore.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/students")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentController {
    final StudentService service;

    @GetMapping("/{id}/read-books")
    public List<StudentReadBookResponse> getReadBooksById(@PathVariable Long id) {
        return service.getReadBookByStudentId(id);
    }
    @GetMapping("/{id}/student-subscribes")
    public List<StudentSubscribeAuthorResponse> getSubscribeAuthorsById(@PathVariable Long id){
        return service.getStudentSubscribesById(id);
    }


    @GetMapping("/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveStudent(@RequestBody SaveStudentRequest request) {
        service.saveStudent(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStudent(@PathVariable Long id, @RequestBody UpdateStudentRequest request) {
        service.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }

    @GetMapping
    public List<StudentResponse> getStudents() {
        return service.getStudents();
    }

    @PostMapping("/readBook")
    @ResponseStatus(CREATED)
    public void saveStudentReadBook(@RequestBody SaveStudentReadBookRequest request) {
        service.saveStudentReadBook(request);
    }

    @PostMapping("/subscription")
    @ResponseStatus(CREATED)
    public void saveStudentSubscription(@RequestBody SaveStudentSubscriptionRequest request) {
        service.saveStudentSubscription(request);
    }
}
