package com.qa.board.repository;

import com.qa.board.domain.QuestionAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAlertRepository extends JpaRepository<QuestionAlert, Long> {
}
