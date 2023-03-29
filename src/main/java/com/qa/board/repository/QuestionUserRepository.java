package com.qa.board.repository;

import com.qa.board.domain.QuestionLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionUserRepository extends JpaRepository<QuestionLikes, Long> {
}
