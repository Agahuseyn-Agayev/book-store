package com.example.ingressbookstore.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum ExceptionMessage {

    STUDENT_NOT_FOUND("Student not found"),

    UNAUTORIZE_USER("Unautorize user"),
    BAD_REQUEST_EXCEPTION("Bad request"),
    AUTHOR_NOT_FOUND("Author not found"),
    BOOK_NOT_FOUND("Book not found"),
    EMAIL_NOT_FOUND("Email not found"),
    EMAIL_EXITS("Email exits"),
    PASSWORD_DOES_NOT_MATCH("Provided password doesn't match"),
    UNEXPECTED_ERROR("Unexpected error occurred");
    final String message;

}
