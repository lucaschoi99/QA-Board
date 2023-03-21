package com.qa.board.service;

import com.qa.board.domain.Question;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);
    }

    public void createQuestion(String title, String content) {
        Question question = Question.builder()
                .title(title)
                .content(content)
                .build();
        questionRepository.save(question);
    }

}
