package com.impact.core.expection.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessageResponse extends MessageResponse {
    private int status;
    private String path;
    private String timestamp;

    public ErrorMessageResponse(int status, String message, String path) {
        super(message);
        this.status = status;
        this.path = path;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}
