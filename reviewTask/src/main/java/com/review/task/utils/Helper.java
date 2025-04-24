package com.review.task.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class Helper {

    @Autowired
    private ObjectMapper objectMapper;

    public <T, U> T convert(Class<T> clazz, U object) {
        return objectMapper.convertValue(object, clazz);
    }

    public <T, U> List<T> convertList(Class<T> clazz, List<U> list) {
        return list.stream().map(m -> objectMapper.convertValue(m, clazz)).collect(Collectors.toList());
    }

    public String generateFileName(String fileName) {
        return UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
    }
}
