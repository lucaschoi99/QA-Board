package com.qa.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(value = NOT_FOUND, reason = "Entity not found")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "존재하지 않는 질문입니다";

    public DataNotFoundException() {
        super(MESSAGE);
    }
}
