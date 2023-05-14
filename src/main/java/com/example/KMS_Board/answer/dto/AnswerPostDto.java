package com.example.KMS_Board.answer.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class AnswerPostDto {
    @Positive
    @NotNull
    private Long questionId;

    @NotBlank(message = "Post Your Answer")
    private String body;
}