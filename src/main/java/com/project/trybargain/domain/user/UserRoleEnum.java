package com.project.trybargain.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleEnum {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    OWNER("ROLE_OWNER");

    private final String authority;
}
