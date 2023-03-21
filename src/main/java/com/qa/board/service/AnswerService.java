package com.qa.board.service;

import com.qa.board.domain.Answer;
import com.qa.board.domain.Question;
import com.qa.board.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answerRepository.save(answer);
    }


}
