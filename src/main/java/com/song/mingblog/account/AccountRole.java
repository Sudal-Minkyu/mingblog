package com.song.mingblog.account;

import lombok.Getter;

@Getter
public enum AccountRole {

    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_USER("ROLE_USER", "사용자");

    private final String code;
    private final String desc;

    AccountRole(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}


