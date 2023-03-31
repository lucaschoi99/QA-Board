package com.qa.board.service;

import com.qa.board.domain.*;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.form.AnswerForm;
import com.qa.board.repository.AnswerRepository;
import com.qa.board.repository.AnswerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerUserRepository answerUserRepository;

    public Answer create(Question question, String content, SiteUser author) {
        return answerRepository.save(Answer.builder()
                .question(question)
                .content(content)
                .author(author)
                .build());
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
        } else {
            answerUserRepository.delete(isExists);
        }
    }
}
