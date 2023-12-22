package com.utc2.riskmanagement.payloads;

import lombok.Data;

@Data
public class JWTAuthRequest {
    private String email;
    private String password;
}
