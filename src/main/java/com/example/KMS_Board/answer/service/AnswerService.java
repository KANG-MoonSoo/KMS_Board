package com.example.KMS_Board.answer.service;

import com.example.KMS_Board.answer.dto.AnswerPatchDto;
import com.example.KMS_Board.answer.dto.UpdateEditor;
import com.example.KMS_Board.question.entity.Question;
import com.example.KMS_Board.question.repository.QuestionRepository;
import com.example.KMS_Board.answer.entity.Answer;
import com.example.KMS_Board.answer.repository.AnswerRepository;
import com.example.KMS_Board.exception.BusinessLogicException;
import com.example.KMS_Board.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }
    //연관게시글 생성
    public Answer createAnswer(Answer answer){
        return answerRepository.save(answer);
    }
    //연관게시글 수정
    @Transactional
    public void updateAnswer(long id, AnswerPatchDto answerPatchDto){
        Answer answer = answerRepository.findById(id).orElseThrow(RuntimeException::new);
        UpdateEditor.UpdateEditorBuilder updateEditorBuilder = answer.toEditor();
        UpdateEditor updateEditor = updateEditorBuilder.body(answerPatchDto.getBody()).build();
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());
        Optional.ofNullable(answer.getUpdatedAt()).ifPresent(answerUpdatedAt -> findAnswer.setUpdatedAt(answerUpdatedAt));
        answerRepository.save(answer);
        answer.edit(updateEditor);
    }
    public Answer findAnswer(long answerId) {
        return findVerifiedAnswer(answerId);
    }
    public Page<Answer> findAnswers(Question question, int answerPage, int answerSize) throws BusinessLogicException{
        Page<Answer> findAllAnswer = answerRepository.findAllByQuestion(
                PageRequest.of(answerPage-1,answerSize, Sort.by("createdAt").descending()), question);
        VerifiedNoAnswer(findAllAnswer);

        return findAllAnswer;
    }
    public Page<Answer> findAnswers(int page, int size) {
        return answerRepository.findAll(PageRequest.of(page, size,
                Sort.by("createdAt").descending()));
    }
    public void deleteAnswer(long answerId) {
        answerRepository.deleteById(answerId);
    }

    public Answer findVerifiedAnswer(long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }
    private void VerifiedNoAnswer(Page<Answer> findAllAnswer) throws BusinessLogicException{
        if(findAllAnswer.getTotalElements()==0){
            throw new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND);
        }
    }
}
