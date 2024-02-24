package com.example.ingressbookstore.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveStudentReadBookRequest {
    Long studentId;
    Long bookId;
}
