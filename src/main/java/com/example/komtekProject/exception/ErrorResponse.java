package com.example.komtekProject.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    private List<ErrorDetail> errors = new ArrayList<>();

    public ErrorResponse() {}

    public ErrorResponse(String code, String message) {
        this.errors.add(new ErrorDetail(code, message));
    }

    public void addError(String code, String message) {
        this.errors.add(new ErrorDetail(code, message));
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public static class ErrorDetail {
        private String code;
        private String message;

        public ErrorDetail() {}

        public ErrorDetail(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
