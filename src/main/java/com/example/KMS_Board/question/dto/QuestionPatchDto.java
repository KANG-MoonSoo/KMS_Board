package com.example.KMS_Board.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class QuestionPatchDto {
    @NotBlank(message = "The title must not be blank.")
    private String title;
    @NotBlank(message = "The content must not be blank.")
    private String content;
}
