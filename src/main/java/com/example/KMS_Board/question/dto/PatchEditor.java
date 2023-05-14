package com.example.KMS_Board.question.dto;

import com.example.KMS_Board.audit.Auditable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchEditor extends Auditable {
    private final String title;
    private final String content;
    @Builder
    public PatchEditor(String title, String content){
        this.title = title;
        this.content = content;
    }

}
