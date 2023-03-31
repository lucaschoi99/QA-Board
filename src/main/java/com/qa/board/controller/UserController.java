package com.qa.board.controller;

import com.qa.board.domain.AnswerAlert;
import com.qa.board.domain.QuestionAlert;
import com.qa.board.domain.SiteUser;
import com.qa.board.form.UserCreateForm;
import com.qa.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signUp")
    public String signUp(UserCreateForm userCreateForm) {
        return "signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid UserCreateForm userCreateForm,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signUpForm";
        }
        if (!userCreateForm.getPassword().equals(userCreateForm.getPasswordAgain())) {
            bindingResult.rejectValue("passwordAgain",
                    "IncorrectPassword", "두 비밀번호가 일치하지 않습니다.");
            return "signUpForm";
        }

        try {
            userService.createUser(userCreateForm);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signUpFailed", "이미 존재하는 회원입니다.");
            return "signUpForm";
        } catch (Exception e) {
            bindingResult.reject("signUpFailed", e.getMessage());
            return "signUpForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/alert")
    public String alert(Model model, Principal principal) {
        SiteUser myUser = userService.getUser(principal.getName());
        List<QuestionAlert> copiedQuestionAlert = new ArrayList<>(myUser.getQuestionAlert());
        List<AnswerAlert> copiedAnswerAlert = new ArrayList<>(myUser.getAnswerAlert());
        reverse(copiedQuestionAlert);
        reverse(copiedAnswerAlert);

        model.addAttribute("questions", copiedQuestionAlert);
        model.addAttribute("answers", copiedAnswerAlert);
        return "showAlert";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String myPage(Model model, Principal principal) {
        SiteUser myUser = userService.getUser(principal.getName());
        model.addAttribute("user", myUser);
        return "myPage";
    }
}
