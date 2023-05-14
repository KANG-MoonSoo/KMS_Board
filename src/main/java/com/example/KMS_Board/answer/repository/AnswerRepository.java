package com.example.KMS_Board.answer.repository;

import com.example.KMS_Board.question.entity.Question;
import com.example.KMS_Board.answer.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAllByQuestion(Pageable pageable, @Param("question")Question question);
}
