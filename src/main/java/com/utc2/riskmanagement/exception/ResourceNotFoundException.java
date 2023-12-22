package com.utc2.riskmanagement.exception;

import com.utc2.riskmanagement.utils.ExceptionConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String resource;
    private String field;
    private String values;

    public ResourceNotFoundException(String resource, String field, String values) {
        super(String.format(ExceptionConstant.RESOURCE_NOT_FOUND, resource, field, values));
        this.resource = resource;
        this.field = field;
        this.values = values;
    }
}
