package com.review.task.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorDetails {
    RECORD_NOT_FOUND(1001, "Record not found!");

    private Integer code;
    private String message;

}
