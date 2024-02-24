package com.example.ingressbookstore.service;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.entity.BookEntity;
import com.example.ingressbookstore.exception.NotFoundException;
import com.example.ingressbookstore.mapper.AuthorMapper;
import com.example.ingressbookstore.model.enums.ExceptionMessage;
import com.example.ingressbookstore.model.enums.Status;
import com.example.ingressbookstore.model.request.CreateBookRequest;
import com.example.ingressbookstore.model.request.SaveAuthorRequest;
import com.example.ingressbookstore.model.request.UpdateAuthorRequest;
import com.example.ingressbookstore.model.response.AuthorBooksResponse;
import com.example.ingressbookstore.model.response.AuthorResponse;
import com.example.ingressbookstore.repository.AuthorRepository;
import com.example.ingressbookstore.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.ingressbookstore.mapper.AuthorMapper.builderAuthorEntity;
import static com.example.ingressbookstore.mapper.AuthorMapper.builderAuthorResponse;
import static com.example.ingressbookstore.model.enums.ExceptionMessage.AUTHOR_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorService {
    final AuthorRepository repository;

    public AuthorResponse getAuthorById(Long id) {
        var author = fetchAuthorIfExits(id);
        return builderAuthorResponse(author);
    }

    public void saveAuthor(SaveAuthorRequest request) {
        repository.save(builderAuthorEntity(request));
    }


    public void updateAuthor(Long id, UpdateAuthorRequest request) {
        var author = fetchAuthorIfExits(id);
        AuthorMapper.updateAuthor(author, request);
        repository.save(author);
    }

    public void deleteAuthor(Long id) {
        var author = fetchAuthorIfExits(id);
        author.setStatus(Status.DELETED);
        repository.save(author);
    }
    public List<AuthorBooksResponse> getAuthorBooksById(Long id){
        return repository.findBookNameByAuthorId(id)
                .stream()
                .map(AuthorBooksResponse::new)
                .toList();
    }

    public List<AuthorResponse> getAuthors() {
        return repository.findAll().stream()
                .map(AuthorMapper::getAuthors).collect(Collectors.toList());
    }

    private AuthorEntity fetchAuthorIfExits(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(AUTHOR_NOT_FOUND.getMessage())
        );
    }

}
