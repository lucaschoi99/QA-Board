package com.qa.board.config;
import com.qa.board.domain.Question;
import com.qa.board.domain.SiteUser;
import com.qa.board.repository.QuestionRepository;
import com.qa.board.repository.UserRepository;
import com.qa.board.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile({"dev", "test"})
// Init : 개발환경이 이거나, 테스트환경일 때만 실행
public class Init {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(QuestionService questionService,
                                      UserRepository userRepository) {
        return args -> {
            // 이 부분은 스프링부트 앱이 실행되고, 본격적으로 작동하기 전에 실행된다.
            String encodedPassword = passwordEncoder.encode("111");

            SiteUser initUser = SiteUser.builder()
                    .username("운영자")
                    .password(encodedPassword)
                    .email("admin@gmail.com")
                    .build();
            userRepository.save(initUser);

            for (int i = 1; i <= 300; i++) {
                String title = "[제목] - " + i;
                String content = "[내용] - " + i;
                questionService.createQuestion(title, content, initUser);
            }

            SiteUser user = SiteUser.builder()
                    .username("bin")
                    .password(encodedPassword)
                    .email("bot@gmail.com")
                    .build();
            userRepository.save(user);
            questionService.createQuestion("bin-1", "bin-1", user);
        };
    }
}