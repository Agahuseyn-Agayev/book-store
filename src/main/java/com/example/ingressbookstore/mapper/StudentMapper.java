package com.example.ingressbookstore.mapper;

import com.example.ingressbookstore.entity.AuthorEntity;
import com.example.ingressbookstore.entity.StudentEntity;
import com.example.ingressbookstore.model.enums.Status;
import com.example.ingressbookstore.model.request.SaveStudentRequest;
import com.example.ingressbookstore.model.request.UpdateStudentRequest;
import com.example.ingressbookstore.model.response.StudentResponse;
import com.example.ingressbookstore.model.response.StudentSubscribeAuthorResponse;

public class StudentMapper {
    public static StudentResponse builderStudentResponse(StudentEntity entity) {
        return StudentResponse.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .age(entity.getAge())
                .build();
    }


    public static StudentEntity builderStudentEntity(SaveStudentRequest request) {
        return StudentEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .age(request.getAge())
                .status(Status.ACTIVE)
                .build();
    }


    public static void updateStudent(StudentEntity entity, UpdateStudentRequest request) {
        entity.setName(request.getName());
        entity.setSurname(request.getSurname());
        entity.setEmail(request.getEmail());
        entity.setAge(request.getAge());
    }

    public static StudentResponse getStudents(StudentEntity studentEntity){
        return StudentResponse.builder()
                .name(studentEntity.getName())
                .surname(studentEntity.getSurname())
                .email(studentEntity.getEmail())
                .age(studentEntity.getAge())
                .build();
    }
}
