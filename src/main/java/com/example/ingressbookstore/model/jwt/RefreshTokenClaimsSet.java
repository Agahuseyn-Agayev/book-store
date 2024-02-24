package com.example.ingressbookstore.model.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshTokenClaimsSet {

    private Long userId;
    private String email;
    private List<String> roles;
    private Date expirationTime;
    private Integer count;
    private String iss;
}
