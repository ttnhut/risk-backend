package com.utc2.riskmanagement.exception;

import com.utc2.riskmanagement.utils.ExceptionConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceExistException extends RuntimeException {
    private String resource;
    private String field;
    private String values;

    public ResourceExistException(String resource, String field, String values) {
        super(String.format(ExceptionConstant.RESOURCE_EXIST, resource, field, values));
        this.resource = resource;
        this.field = field;
        this.values = values;
    }
}
