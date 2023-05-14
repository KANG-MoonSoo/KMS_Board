package com.example.KMS_Board.answer.dto;

import com.example.KMS_Board.answer.entity.Answer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerResponseDto {
    private long answerId;
    private String body;
    private Answer.AnswerStatus answerStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
