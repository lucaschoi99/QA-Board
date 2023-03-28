package com.qa.board.controller;

import com.qa.board.domain.Question;
import com.qa.board.domain.SiteUser;
import com.qa.board.form.AnswerForm;
import com.qa.board.service.AnswerService;
import com.qa.board.service.QuestionService;
import com.qa.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/answer/create/{id}")
    public String createAnswer(Model model, @PathVariable Long id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) {
        Question question = questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "questionDetail";
        }
        SiteUser user = userService.getUser(principal.getName());
        answerService.create(question, answerForm.getContent(), user);
        return "redirect:/question/detail/" + id;
    }
}
