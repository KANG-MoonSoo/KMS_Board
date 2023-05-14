package com.example.KMS_Board.answer.controller;

import com.example.KMS_Board.question.service.QuestionService;
import com.example.KMS_Board.answer.dto.AnswerPatchDto;
import com.example.KMS_Board.answer.dto.AnswerPostDto;
import com.example.KMS_Board.answer.entity.Answer;
import com.example.KMS_Board.answer.mapper.AnswerMapper;
import com.example.KMS_Board.answer.service.AnswerService;
import com.example.KMS_Board.response.SingleResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Api(tags = {"Answer API"})
@RestController
@Validated
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private AnswerService answerService;
    private AnswerMapper mapper;
    private QuestionService questionService;

    // 연관게시글 등록
    @PostMapping
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto) {
        Answer question = answerService.createAnswer(
                mapper.answerPostDtoToAnswer(questionService,answerPostDto));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.answerToAnswerResponseDto(question)), HttpStatus.CREATED);
    }
    //연관게시글 수정
    @PatchMapping("/{answer-id}")
    public void patchAnswer(@PathVariable("answer-id") @Positive @NotNull long answerId,
                                      @Valid @RequestBody AnswerPatchDto answerPatchDto) {
        answerService.updateAnswer(answerId, answerPatchDto);
    }
    //연관게시글 삭제
    @DeleteMapping(value= "/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive @NotNull long answerId){

        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
