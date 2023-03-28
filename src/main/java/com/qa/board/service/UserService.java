package com.qa.board.service;

import com.qa.board.domain.SiteUser;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.form.UserCreateForm;
import com.qa.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SiteUser createUser(UserCreateForm userCreate) {
        String encodedPassword = passwordEncoder.encode(userCreate.getPassword());

        return userRepository.save(SiteUser.builder()
                .username(userCreate.getUsername())
                .password(encodedPassword)
                .email(userCreate.getEmail())
                .build());
    }

    public SiteUser getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("사용자가 존재하지 않습니다."));
    }


}
