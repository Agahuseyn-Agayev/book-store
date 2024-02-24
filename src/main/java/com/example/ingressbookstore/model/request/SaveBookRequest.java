package com.example.ingressbookstore.model.request;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.repository.AuthorRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveBookRequest {

    Long authorId;
    String name;
    String description;
}
