package com.example.ingressbookstore.mapper;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.model.enums.Status;
import com.example.ingressbookstore.model.request.SaveAuthorRequest;
import com.example.ingressbookstore.model.request.UpdateAuthorRequest;
import com.example.ingressbookstore.model.response.AuthorResponse;

public class AuthorMapper {

    public static AuthorResponse builderAuthorResponse(AuthorEntity entity) {
        return AuthorResponse.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .age(entity.getAge())
                .build();
    }

    public static AuthorEntity builderAuthorEntity(SaveAuthorRequest request) {
        return AuthorEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .age(request.getAge())
                .status(Status.ACTIVE)
                .build();
    }

    public static void updateAuthor(AuthorEntity entity, UpdateAuthorRequest request) {
        entity.setName(request.getName());
        entity.setSurname(request.getSurname());
        entity.setAge(request.getAge());
    }

    public static AuthorResponse getAuthors(AuthorEntity authorEntity) {
        return AuthorResponse.builder()
                .name(authorEntity.getName())
                .surname(authorEntity.getSurname())
                .age(authorEntity.getAge())
                .build();

    }
}
