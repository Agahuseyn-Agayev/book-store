package com.example.ingressbookstore.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAuthorRequest {
    String name;
    String surname;
    Long age;
}
