package com.qa.board.service;

import com.qa.board.domain.Question;
import com.qa.board.domain.SiteUser;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.form.QuestionEdit;
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

    public Page<Question> findQuestions(int page, int pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("createdDate")));
        return questionRepository.findAll(pageable);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 질문입니다"));
    }

    public void createQuestion(String title, String content, SiteUser author) {
        questionRepository.save(Question.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
    }

    public void edit(Question question, QuestionEdit questionEdit) {
        question.edit(questionEdit.getTitle(), questionEdit.getContent());
        questionRepository.save(question);
    }
}
