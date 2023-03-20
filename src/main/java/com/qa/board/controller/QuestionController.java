package com.qa.board.controller;

import com.qa.board.domain.Question;
import com.qa.board.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String getList(Model model) {
        List<Question> questions = questionService.findQuestions();
        model.addAttribute("questionList", questions);
        return "questionList";
    }
}
