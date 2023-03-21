package com.qa.board.controller;

import com.qa.board.domain.Question;
import com.qa.board.form.AnswerForm;
import com.qa.board.form.QuestionForm;
import com.qa.board.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    // TODO: Controller에서 모두 직접 엔티티 받지 말고 DTO 클래스 만들어 수행.

    @GetMapping("/list")
    public String getList(Model model) {
        List<Question> questions = questionService.findQuestions();
        model.addAttribute("questionList", questions);
        return "questionList";
    }

    @GetMapping("/detail/{id}")
    public String details(Model model, @PathVariable Long id,
                          AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "questionDetail";
    }

    @GetMapping("/create")
    public String questionForm(QuestionForm questionForm) {
        return "questionForm";
    }

    @PostMapping("/create")
    public String createQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "questionForm";
        }
        questionService.createQuestion(questionForm.getTitle(), questionForm.getContent());
        return "redirect:/";
    }


}
