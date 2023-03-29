package com.qa.board.repository;

import com.qa.board.domain.AnswerLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerUserRepository extends JpaRepository<AnswerLikes, Long> {
}
