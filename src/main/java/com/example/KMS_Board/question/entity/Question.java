package com.example.KMS_Board.question.entity;

import com.example.KMS_Board.question.dto.PatchEditor;
import com.example.KMS_Board.answer.entity.Answer;
import com.example.KMS_Board.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "QUESTION")
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, name = "STATUS")
    private QuestionStatus questionStatus = QuestionStatus.QUESTION_EXIST;


    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();
    public PatchEditor.PatchEditorBuilder toEditor() {
        return PatchEditor.builder()
                .title(title)
                .content(content);
    }
    public void edit(PatchEditor patchEditor) {
        title = patchEditor.getTitle();
        content = patchEditor.getContent();
    }

    public enum QuestionStatus {
        QUESTION_NOT_EXIST("존재하지 않는 질문"),
        QUESTION_EXIST("존재하는 질문");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }

}
