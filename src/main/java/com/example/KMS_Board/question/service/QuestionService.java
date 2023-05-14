package com.example.KMS_Board.question.service;

import com.example.KMS_Board.question.dto.PatchEditor;
import com.example.KMS_Board.question.dto.QuestionPatchDto;
import com.example.KMS_Board.question.entity.Question;
import com.example.KMS_Board.question.repository.QuestionRepository;
import com.example.KMS_Board.exception.BusinessLogicException;
import com.example.KMS_Board.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    public QuestionService(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }
    // 게시글 생성
    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }
    //게시글 수정
    @Transactional
    public void updateQuestion(long id, QuestionPatchDto questionPatchDto){
        Question question = questionRepository.findById(id).orElseThrow(RuntimeException::new);
        PatchEditor.PatchEditorBuilder patchEditorBuilder = question.toEditor();
        PatchEditor patchEditor = patchEditorBuilder.title(questionPatchDto.getTitle())
                .content(questionPatchDto.getContent()).build();
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());
        Optional.ofNullable(question.getUpdatedAt()).ifPresent(questionUpdatedAt -> findQuestion.setUpdatedAt(questionUpdatedAt));
        questionRepository.save(question);
        question.edit(patchEditor);
    }
    //특정 게시글 조회
    public Question findQuestion(long questionId){
        Question findQuestion = findVerifiedQuestion(questionId);
        return findQuestion;
    }
    //모든 게시글 조회
    public Page<Question> findQuestions(int page, int size){
        Page<Question> questions = questionRepository.findAllByQuestionStatus(
                PageRequest.of(page, size, Sort.by("createdAt").descending()),
                Question.QuestionStatus.QUESTION_EXIST);
        VerifiedNoQuestion(questions);
        return questions;
    }
    //게시글 삭제
    public void deleteQuestion(long questionId){
        questionRepository.deleteById(questionId);
    }

    public Question findVerifiedQuestion(long questionId){
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }

    private void VerifiedNoQuestion(Page<Question> findAllQuestion){
        if(findAllQuestion.getTotalElements()==0){
            throw new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND);
        }
    }

}
