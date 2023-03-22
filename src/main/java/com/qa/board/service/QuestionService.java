package com.qa.board.service;

import com.qa.board.domain.Question;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> findQuestions(int page) {
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createdDate")));
        return questionRepository.findAll(pageable);
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
