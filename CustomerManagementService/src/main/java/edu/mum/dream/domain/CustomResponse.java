package edu.mum.dream.domain;

public class CustomResponse {
    private String message;
    private int code;

    public CustomResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
