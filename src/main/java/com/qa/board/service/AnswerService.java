package com.qa.board.service;

import com.qa.board.domain.Answer;
import com.qa.board.domain.Question;
import com.qa.board.domain.SiteUser;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.form.AnswerForm;
import com.qa.board.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) {
        answerRepository.save(Answer.builder()
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
}
