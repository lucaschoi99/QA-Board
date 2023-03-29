package com.qa.board.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionEdit {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Size(max = 200)
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public QuestionEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
