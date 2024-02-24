package com.example.ingressbookstore.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class TokenRequest {
    private String accessToken;
    private String refreshToken;
}
