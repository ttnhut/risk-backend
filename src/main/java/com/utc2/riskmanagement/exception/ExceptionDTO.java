package com.utc2.riskmanagement.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionDTO implements Serializable {
    private String message;
    private Date timestamp;
    private String path;
    private String code;
}
