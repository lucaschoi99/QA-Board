package com.qa.board.repository;

import com.qa.board.domain.AnswerAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerAlertRepository extends JpaRepository<AnswerAlert, Long> {
}
