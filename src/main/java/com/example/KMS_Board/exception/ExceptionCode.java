package com.example.KMS_Board.exception;

import lombok.Getter;

public enum ExceptionCode {
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(404, "Not Question");
    @Getter
    private int status;
    @Getter
    private String message;
    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
