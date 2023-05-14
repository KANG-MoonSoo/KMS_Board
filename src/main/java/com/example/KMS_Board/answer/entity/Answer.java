package com.example.KMS_Board.answer.entity;

import com.example.KMS_Board.answer.dto.UpdateEditor;
import com.example.KMS_Board.question.entity.Question;
import com.example.KMS_Board.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ANSWERS")
public class Answer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATUS")
    private AnswerStatus answerStatus = AnswerStatus.ANSWER_EXIST;

    public UpdateEditor.UpdateEditorBuilder toEditor(){
        return UpdateEditor.builder()
                .body(body);
    }
    public void edit(UpdateEditor updateEditor){
        body = updateEditor.getBody();
    }
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
    public enum AnswerStatus {
        ANSWER_NOT_EXIST("존재하지 않는 답변"),
        ANSWER_EXIST("존재하는 답변");
        @Getter
        private String status;
        AnswerStatus(String status) {
            this.status = status;
        }
    }
}
