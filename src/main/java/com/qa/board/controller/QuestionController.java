package com.qa.board.controller;

import com.qa.board.domain.Question;
import com.qa.board.domain.SiteUser;
import com.qa.board.form.AnswerForm;
import com.qa.board.form.QuestionEdit;
import com.qa.board.form.QuestionForm;
import com.qa.board.service.QuestionService;
import com.qa.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String getList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        int pageSize = 10;
        Page<Question> paged = questionService.findQuestions(page, pageSize, keyword);
        model.addAttribute("paged", paged);
        model.addAttribute("keyword", keyword);
        return "questionList";
    }

    @GetMapping("/detail/{id}")
    public String details(Model model, @PathVariable Long id,
                          AnswerForm answerForm, Principal principal) {
        Question question = questionService.getQuestion(id);

        if (principal != null) {
            questionService.addCounts(question, principal.getName());
        }
        model.addAttribute("question", question);
        return "questionDetail";
    }

    @GetMapping("/create")
    public String questionForm(QuestionForm questionForm) {
        return "questionForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            return "questionForm";
        }
        SiteUser user = userService.getUser(principal.getName());
        questionService.createQuestion(questionForm.getTitle(), questionForm.getContent(), user);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, Principal principal) {
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(BAD_REQUEST, "수정 권한이 없습니다.");
        }
        model.addAttribute("questionForm", new QuestionEdit(
                question.getTitle(),
                question.getContent()
        ));
        return "questionForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public String edit(@Valid @ModelAttribute("questionForm") QuestionEdit questionEdit, BindingResult bindingResult,
                       @PathVariable Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "questionForm";
        }
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionService.edit(question, questionEdit);
        return "redirect:/question/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {
        Question question = questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/likes/{id}")
    public String questionLikes(Principal principal, @PathVariable Long id) {
        Question question = questionService.getQuestion(id);
        SiteUser user = userService.getUser(principal.getName());

        questionService.addLikes(question, user);
        return "redirect:/question/detail/" + id;
    }




}
