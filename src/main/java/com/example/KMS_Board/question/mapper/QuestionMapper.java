package com.example.KMS_Board.question.mapper;

import com.example.KMS_Board.question.dto.QuestionAndAnswerResponseDto;
import com.example.KMS_Board.question.dto.QuestionPatchDto;
import com.example.KMS_Board.question.dto.QuestionPostDto;
import com.example.KMS_Board.question.dto.QuestionResponseDto;
import com.example.KMS_Board.question.entity.Question;
import com.example.KMS_Board.answer.entity.Answer;
import com.example.KMS_Board.answer.mapper.AnswerMapper;
import com.example.KMS_Board.answer.service.AnswerService;
import com.example.KMS_Board.exception.BusinessLogicException;
import com.example.KMS_Board.response.MultiResponseDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    default Question questionPostDtoToQuestion(QuestionPostDto questionPostDto){
        Question question = new Question();
        question.setTitle(questionPostDto.getTitle());
        question.setContent(questionPostDto.getContent());
        return question;
    }
    default QuestionResponseDto questionToQuestionResponseDto(Question question){
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setContent(question.getContent());
        questionResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionResponseDto.setCreatedAt(question.getCreatedAt());
        questionResponseDto.setUpdatedAt(question.getUpdatedAt());

        return questionResponseDto;
    }
    default List<QuestionResponseDto> questionsToQuestionResponseDtos(List<Question> questions){
        if (questions == null) {
            return null;
        }
        List<QuestionResponseDto> list = new ArrayList<QuestionResponseDto>(questions.size());
        for (Question question : questions){
            QuestionResponseDto questionResponseDto = new QuestionResponseDto();
            questionResponseDto.setQuestionId( question.getQuestionId() );
            questionResponseDto.setTitle( question.getTitle() );
            questionResponseDto.setContent( question.getContent() );
            questionResponseDto.setQuestionStatus( question.getQuestionStatus() );
            questionResponseDto.setAnswerCnt(question.getAnswers().size());
            questionResponseDto.setCreatedAt( question.getCreatedAt() );
            questionResponseDto.setUpdatedAt( question.getUpdatedAt() );

            list.add( questionResponseDto );
        }

        return list;
    }
    default QuestionAndAnswerResponseDto questionToQuestionAndAnswerResponseDtos(AnswerService answerService, AnswerMapper answerMapper,
                                                                                 Question question, Integer answerPage, Integer answerSize){
        QuestionAndAnswerResponseDto questionAndAnswerResponseDto = new QuestionAndAnswerResponseDto();
        questionAndAnswerResponseDto.setQuestionId(question.getQuestionId());
        questionAndAnswerResponseDto.setTitle(question.getTitle());
        questionAndAnswerResponseDto.setContent(question.getContent());
        questionAndAnswerResponseDto.setQuestionStatus(question.getQuestionStatus());
        questionAndAnswerResponseDto.setCreatedAt(question.getCreatedAt());
        questionAndAnswerResponseDto.setUpdatedAt(question.getUpdatedAt());

        try{
            Page<Answer> pageAnswers = answerService.findAnswers(
                    question,answerPage,answerSize);
            List<Answer> answers = pageAnswers.getContent();
            questionAndAnswerResponseDto.setAnswers(new MultiResponseDto<>(
                    answerMapper.answersToAnswersResponseDtos(answers), pageAnswers));
        }catch(BusinessLogicException e){

        }


        return questionAndAnswerResponseDto;
    }
}
