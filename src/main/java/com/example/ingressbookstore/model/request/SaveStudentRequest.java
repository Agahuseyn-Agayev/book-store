package com.example.ingressbookstore.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveStudentRequest {
    String name;
    String surname;
    String email;
    Long age;
}
