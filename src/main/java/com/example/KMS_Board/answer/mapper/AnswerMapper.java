package com.example.KMS_Board.answer.mapper;

import com.example.KMS_Board.question.service.QuestionService;
import com.example.KMS_Board.answer.dto.AnswerPatchDto;
import com.example.KMS_Board.answer.dto.AnswerPostDto;
import com.example.KMS_Board.answer.dto.AnswerResponseDto;
import com.example.KMS_Board.answer.entity.Answer;
import com.example.KMS_Board.answer.service.AnswerService;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    default Answer answerPostDtoToAnswer(QuestionService questionService, AnswerPostDto answerPostDto){
        Answer answer = new Answer();
        answer.setBody(answerPostDto.getBody());
        answer.setQuestion(questionService.findVerifiedQuestion(answerPostDto.getQuestionId()));
        return answer;
    }
    default AnswerResponseDto answerToAnswerResponseDto(Answer answer){
        AnswerResponseDto answerResponseDto = new AnswerResponseDto();
        answerResponseDto.setAnswerId(answer.getAnswerId());
        answerResponseDto.setAnswerStatus(answer.getAnswerStatus());
        answerResponseDto.setBody(answer.getBody());
        answerResponseDto.setCreatedAt(answer.getCreatedAt());
        answerResponseDto.setUpdatedAt(answer.getUpdatedAt());

        return answerResponseDto;
    }
    default List<AnswerResponseDto> answersToAnswersResponseDtos(List<Answer> answers){
        if ( answers == null ) {
            return null;
        }

        List<AnswerResponseDto> list = new ArrayList<>(answers.size());
        for ( Answer answer : answers ) {
            AnswerResponseDto answerResponseDto = new AnswerResponseDto();

            if ( answer.getAnswerId() != null ) {
                answerResponseDto.setAnswerId( answer.getAnswerId() );
            }
            answerResponseDto.setAnswerStatus( answer.getAnswerStatus() );
            answerResponseDto.setBody( answer.getBody() );
            answerResponseDto.setCreatedAt( answer.getCreatedAt() );
            answerResponseDto.setUpdatedAt( answer.getUpdatedAt() );
            list.add( answerResponseDto );
        }
        return list;
    }

}
