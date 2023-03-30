package com.qa.board.repository;

import com.qa.board.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuerydslPredicateExecutor<Question> {
    Question findByTitle(String title);
    Question findByTitleAndContent(String title, String content);
    List<Question> findByTitleLike(String title);
    Page<Question> findAll(Pageable pageable);
}
