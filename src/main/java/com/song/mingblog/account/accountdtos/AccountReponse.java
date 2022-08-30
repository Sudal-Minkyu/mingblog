package com.song.mingblog.account.accountdtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountReponse {

    private final Long id;
    private final String userid;
    private final String password;
    private final String userName;
    private final AccountRole role;
    private final String insert_id;
    private final LocalDateTime insert_date;

    @Builder
    public AccountReponse(Long id, String userid, String password, String userName, AccountRole role, String insert_id, LocalDateTime insert_date) {
        this.id = id;
        this.userid = userid;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.insert_id = insert_id;
        this.insert_date = insert_date;
    }
}
