package com.utc2.riskmanagement.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtil {
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
