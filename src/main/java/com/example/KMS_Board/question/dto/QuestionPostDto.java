package com.example.KMS_Board.question.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class QuestionPostDto {
    @NotBlank(message = "The title must not be blank")
    private String title;

    @NotBlank(message = "The title must not be blank")
    private String content;
}
