package com.example.ingressbookstore.controller;

import com.example.ingressbookstore.model.request.CreateBookRequest;
import com.example.ingressbookstore.model.request.SaveAuthorRequest;
import com.example.ingressbookstore.model.request.UpdateAuthorRequest;
import com.example.ingressbookstore.model.response.AuthorBooksResponse;
import com.example.ingressbookstore.model.response.AuthorResponse;
import com.example.ingressbookstore.service.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorController {
    final AuthorService service;

    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable Long id) {
        return service.getAuthorById(id);
    }

    @GetMapping("{id}/author-books")
    public List<AuthorBooksResponse> getAuthorBooksById(@PathVariable Long id){
        return service.getAuthorBooksById(id);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public void saveAuthor(@RequestBody SaveAuthorRequest request) {
        service.saveAuthor(request);
    }


    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateAuthor(@PathVariable Long id, @RequestBody UpdateAuthorRequest request) {
        service.updateAuthor(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        service.deleteAuthor(id);
    }

    @GetMapping
    public List<AuthorResponse> getAuthors() {
        return service.getAuthors();
    }
}
