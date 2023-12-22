package com.utc2.riskmanagement.payloads;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String token;
    private UserDTO userDTO;
}
