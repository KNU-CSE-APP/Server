package com.knu.cse.ApiResult;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
    private String response;
    private String message;
    private Object data;

    public Response(String response, String message, Object data) {
        this.response = response;
        this.message = message;
        this.data = data;
    }

}