package com.impact.core.expection.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MessageResponse {
    protected String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}