package com.qa.board.controller;

import com.qa.board.domain.Answer;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Long id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) {
        Question question = questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "questionDetail";
        }
        SiteUser user = userService.getUser(principal.getName());
        Answer answer = answerService.create(question, answerForm.getContent(), user);
        return "redirect:/question/detail/" + id + "#answer_" + answer.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public String edit(AnswerForm answerForm, @PathVariable Long id, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answerForm";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public String edit(@Valid AnswerForm answerForm, BindingResult bindingResult,
                       @PathVariable Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answerForm";
        }
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerService.edit(answer, answerForm);
        return "redirect:/question/detail/" + answer.getQuestion().getId()
                + "#answer_" + answer.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable Long id) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        answerService.delete(answer);
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/likes/{id}")
    public String answerLikes(Principal principal, @PathVariable Long id) {
        Answer answer = answerService.getAnswer(id);
        SiteUser user = userService.getUser(principal.getName());
        answerService.addLikes(answer, user);
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

}
