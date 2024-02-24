package com.example.ingressbookstore.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveAuthorRequest {
    String name;
    String surname;
    Long age;
}
