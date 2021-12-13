package com.example.takepicture;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private String code;
    private String message;
    private List<String> errors;

    public Model(List<String> errors) {
        this.errors = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
