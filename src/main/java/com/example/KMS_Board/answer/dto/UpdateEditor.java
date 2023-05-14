package com.example.KMS_Board.answer.dto;

import com.example.KMS_Board.audit.Auditable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateEditor extends Auditable {
    private final String body;
    @Builder
    public UpdateEditor(String body){
        this.body = body;
    }
}
