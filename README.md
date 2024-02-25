# Bookstore API

Welcome to the Bookstore API repository! This API provides endpoints for managing authentication, authors, books, and student-related operations.

## Authentication
- **Sign Up:** `POST /v1/auth/signUp` - Create a new user account.
- **Sign In:** `POST /v1/auth/signIn` - Sign in and receive an access token.
- **Refresh Token:** `POST /v1/auth/refresh-token` - Refresh the access token using a refresh token.
- **Validate Token:** `POST /v1/auth/validate-token` - Validate the provided access token.

## Authors
- **Get Author by ID:** `GET /v1/authors/{id}` - Get author details by ID.
- **Get Author Books:** `GET /v1/authors/{id}/author-books` - Get books written by a specific author.
- **Create Author:** `POST /v1/authors` - Create a new author.
- **Update Author:** `PUT /v1/authors/{id}` - Update author details.
- **Delete Author:** `DELETE /v1/authors/{id}` - Delete an author.
- **Get Authors:** `GET /v1/authors` - Get a list of all authors.

## Books
- **Get Book by ID:** `GET /v1/books/{id}` - Get book details by ID.
- **Create Book:** `POST /v1/books` - Create a new book.
- **Update Book:** `PUT /v1/books/{id}` - Update book details.
- **Delete Book:** `DELETE /v1/books/{id}` - Delete a book.
- **Get Books:** `GET /v1/books` - Get a list of all books.

## Students
- **Get Read Books by Student ID:** `GET /v1/students/{id}/read-books` - Get books read by a specific student.
- **Get Subscribed Authors by Student ID:** `GET /v1/students/{id}/student-subscribes` - Get authors subscribed to by a specific student.
- **Get Student by ID:** `GET /v1/students/{id}` - Get student details by ID.
- **Create Student:** `POST /v1/students` - Create a new student.
- **Update Student:** `PUT /v1/students/{id}` - Update student details.
- **Delete Student:** `DELETE /v1/students/{id}` - Delete a student.
- **Get Students:** `GET /v1/students` - Get a list of all students.
- **Save Student Read Book:** `POST /v1/students/readBook` - Record a book read by a student.
- **Save Student Subscription:** `POST /v1/students/subscription` - Subscribe a student to an author.
