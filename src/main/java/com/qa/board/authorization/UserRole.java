package com.qa.board.authorization;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN,
    USER;

    UserRole() {
    }
}
