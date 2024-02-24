package com.example.ingressbookstore.mapper;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.entity.BookEntity;
import com.example.ingressbookstore.model.enums.Status;
import com.example.ingressbookstore.model.request.SaveBookRequest;
import com.example.ingressbookstore.model.request.UpdateBookRequest;
import com.example.ingressbookstore.model.response.BookResponse;

public class BookMapper {

    public static BookResponse builderBookResponse(BookEntity entity){
        return BookResponse.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .authorId(entity.getAuthor().getId())
                .build();
    }
    public static BookEntity builderBookEntity(SaveBookRequest request, AuthorEntity author){
        return BookEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .author(author)
                .status(Status.ACTIVE)
                .build();
    }
    public static void updateBook(BookEntity entity, UpdateBookRequest request){
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

    public static BookResponse getBooks(BookEntity bookEntity){
        return BookResponse.builder()
                .name(bookEntity.getName())
                .description(bookEntity.getDescription())
                .authorId(bookEntity.getAuthor().getId())
                .build();
    }







}
