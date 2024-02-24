package com.example.ingressbookstore.model.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessTokenClaimsSet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String email;
    private List<String> roles;
    private String iss;
    @JsonProperty("exp")
    private Date expirationTime;
    @JsonProperty("iat")
    private Date createdTime;


}
