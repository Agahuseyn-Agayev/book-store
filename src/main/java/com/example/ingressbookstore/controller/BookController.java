package com.example.ingressbookstore.controller;

import com.example.ingressbookstore.model.request.SaveBookRequest;
import com.example.ingressbookstore.model.request.UpdateBookRequest;
import com.example.ingressbookstore.model.response.BookResponse;
import com.example.ingressbookstore.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {
    final BookService service;

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveBook(@RequestBody SaveBookRequest request) {
        service.saveBook(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateBook(@PathVariable Long id, @RequestBody UpdateBookRequest request) {
        service.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
    }

    @GetMapping
    public List<BookResponse> getBooks() {
        return service.getBooks();
    }
}
