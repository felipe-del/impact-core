package com.impact.core.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseWrapper<T> {
    private String message;
    private T data;

    public ResponseWrapper(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public ResponseWrapper(String message) {
        this.message = message;
    }
}
