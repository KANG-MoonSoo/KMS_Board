package com.example.KMS_Board.question.controller;

import com.example.KMS_Board.question.dto.QuestionPatchDto;
import com.example.KMS_Board.question.dto.QuestionPostDto;
import com.example.KMS_Board.question.entity.Question;
import com.example.KMS_Board.question.mapper.QuestionMapper;
import com.example.KMS_Board.question.service.QuestionService;
import com.example.KMS_Board.answer.mapper.AnswerMapper;
import com.example.KMS_Board.answer.service.AnswerService;
import com.example.KMS_Board.response.MultiResponseDto;
import com.example.KMS_Board.response.SingleResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/question")
@Validated
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = {"Question API"})
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final AnswerService answerService;

    //게시글 등록
    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){

        Question question = questionService.createQuestion(questionMapper.questionPostDtoToQuestion(questionPostDto));

        return new ResponseEntity<>(new SingleResponseDto<>(questionMapper.questionToQuestionResponseDto(question)), HttpStatus.CREATED);
    }
    //게시글 수정
    @PatchMapping("/{question-id}")
    public void patchQuestion(@PathVariable("question-id") @Positive long questionId, @RequestBody @Valid QuestionPatchDto questionPatchDto){
        questionService.updateQuestion(questionId, questionPatchDto);
    }
    //특정 게시글 조회
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive long questionId,
                                      @Positive @RequestParam("page") int answerPage,
                                      @Positive @RequestParam("size") int answerSize){
        Question question = questionService.findQuestion(questionId);
        return new ResponseEntity<>(new SingleResponseDto<>(
                questionMapper.questionToQuestionAndAnswerResponseDtos(answerService,answerMapper,question,
                        answerPage,answerSize)), HttpStatus.OK);
    }
    //모든 게시글 조회
    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam("page") int page,
                                       @Positive @RequestParam("size") int size){
        Page<Question> pageQuestions = questionService.findQuestions(page-1,size);

        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(
                questionMapper.questionsToQuestionResponseDtos(questions),
                pageQuestions),HttpStatus.OK);
    }
    //게시글 삭제
    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive long questionId){
        System.out.println("delete question");
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
