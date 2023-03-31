package com.qa.board.service;

import com.qa.board.domain.*;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.form.AnswerForm;
import com.qa.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerUserRepository answerUserRepository;
    private final UserRepository userRepository;
    private final QuestionAlertRepository questionAlertRepository;
    private final AnswerAlertRepository answerAlertRepository;

    public Answer create(Question question, String content, SiteUser author, String commentedUserName) {
        Answer answer = answerRepository.save(Answer.builder()
                .question(question)
                .content(content)
                .author(author)
                .build());

        // 질문글에 답변이 달렸음을 알림
        SiteUser user = userRepository.findByUsername(commentedUserName)
                .orElseThrow(IllegalAccessError::new);
        if (user != question.getAuthor()) {
            QuestionAlert alert = QuestionAlert.alertAuthor(user, question.getAuthor(), question.getTitle());
            questionAlertRepository.save(alert);
        }
        return answer;
    }

    public Answer getAnswer(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 답변입니다."));
    }

    public void edit(Answer answer, AnswerForm answerForm) {
        answer.edit(answerForm.getContent());
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    @Transactional
    public void addLikes(Answer answer, SiteUser user) {
        AnswerLikes isExists = answerUserRepository.findByAnswerAndUser(answer, user);
        if (isExists == null) {
            AnswerLikes answerLikes = AnswerLikes.create(answer, user);
            answerUserRepository.save(answerLikes);

            // 좋아요 알림
            if (user != answer.getAuthor()) {
                AnswerAlert alert = AnswerAlert.createMessage(user, answer.getAuthor(), answer.getContent());
                answerAlertRepository.save(alert);
            }
        } else {
            answerUserRepository.delete(isExists);
        }
    }
}
