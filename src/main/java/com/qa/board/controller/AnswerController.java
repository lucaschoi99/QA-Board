package com.qa.board.controller;

import com.qa.board.domain.Question;
import com.qa.board.service.AnswerService;
import com.qa.board.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/answer/create/{id}")
    public String createAnswer(@Valid Model model, @PathVariable Long id,
                               @RequestParam String content) {
        Question question = questionService.getQuestion(id);
        answerService.create(question, content);
        return "redirect:/question/detail/" + id;
    }
}
