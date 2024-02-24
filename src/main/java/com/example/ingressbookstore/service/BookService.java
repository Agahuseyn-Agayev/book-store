package com.example.ingressbookstore.service;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.entity.BookEntity;
import com.example.ingressbookstore.exception.NotFoundException;
import com.example.ingressbookstore.mapper.BookMapper;
import com.example.ingressbookstore.model.enums.ExceptionMessage;
import com.example.ingressbookstore.model.enums.Status;
import com.example.ingressbookstore.model.request.SaveBookRequest;
import com.example.ingressbookstore.model.request.UpdateBookRequest;
import com.example.ingressbookstore.model.response.BookResponse;
import com.example.ingressbookstore.repository.AuthorRepository;
import com.example.ingressbookstore.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.ingressbookstore.model.enums.ExceptionMessage.AUTHOR_NOT_FOUND;
import static com.example.ingressbookstore.model.enums.ExceptionMessage.BOOK_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookService {
    final BookRepository repository;
    final AuthorRepository authorRepository;
    final MailSenderService mailSenderService;

    public BookResponse getBookById(Long id) {
        var book = fetchBookIfExits(id);
        return BookMapper.builderBookResponse(book);
    }

    @Async
    public void saveBook(SaveBookRequest request) {
        AuthorEntity author = fetchAuthorIfExits(request.getAuthorId());
        var book = BookMapper.builderBookEntity(request, author);

        new Thread(()->asyncSendMailToSubscribers(request,author,book)).start();
        asyncSendMailToSubscribers(request, author, book);
        repository.save(book);

    }

    @Async
    void asyncSendMailToSubscribers(SaveBookRequest request, AuthorEntity author, BookEntity book) {
        authorRepository.findSubscribedStudentsById(request.getAuthorId()).forEach(
                subscribedStudent -> {
                    mailSenderService.sendEmail(subscribedStudent, author, book.getName());
                }
        );
    }

    public void updateBook(Long authorId, UpdateBookRequest request) {
        var book = fetchEntityIfByAuthorId(authorId);
        BookMapper.updateBook(book, request);
        repository.save(book);
    }

    public void deleteBook(Long authorId) {
        var book = fetchEntityIfByAuthorId(authorId);
        book.setStatus(Status.DELETED);
        repository.save(book);
    }

    public List<BookResponse> getBooks() {
        return repository.findAll().stream()
                .map(BookMapper::getBooks).collect(Collectors.toList());
    }

    private BookEntity fetchBookIfExits(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(BOOK_NOT_FOUND.getMessage())
        );
    }

    private BookEntity fetchEntityIfByAuthorId(Long authorId) {
        return repository.findByAuthorId(authorId).orElseThrow(
                () -> new RuntimeException(BOOK_NOT_FOUND.getMessage())
        );
    }

    private AuthorEntity fetchAuthorIfExits(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(
                () -> new RuntimeException(AUTHOR_NOT_FOUND.getMessage())
        );
    }
}
