package com.example.KMS_Board.question.dto;

import com.example.KMS_Board.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionResponseDto {
    private long questionId;
    private String title;
    private String content;
    private Question.QuestionStatus questionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int answerCnt;
}
