package com.impact.core.expection.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessMessageResponse extends MessageResponse {
    public SuccessMessageResponse(String message) {
        super(message);
    }
}
