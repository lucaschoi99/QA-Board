package com.qa.board.controller;

import com.qa.board.form.UserCreateForm;
import com.qa.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
