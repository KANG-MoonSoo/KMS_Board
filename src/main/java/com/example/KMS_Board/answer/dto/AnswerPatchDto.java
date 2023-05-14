package com.example.KMS_Board.answer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AnswerPatchDto {
    @NotBlank(message = "The content must not be blank.")
    private String body;
}
