package com.example.KMS_Board.question.dto;

import com.example.KMS_Board.question.entity.Question;
import com.example.KMS_Board.answer.dto.AnswerResponseDto;
import com.example.KMS_Board.response.MultiResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionAndAnswerResponseDto {
    private Long questionId;
    private String title;
    private String content;
    private Question.QuestionStatus questionStatus;
    private MultiResponseDto<AnswerResponseDto> answers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
