package com.song.mingblog.config;

import com.song.mingblog.account.Account;
import com.song.mingblog.account.AccountRole;
import com.song.mingblog.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Date : 2022-07-12
 * Time :
 * Remark : 최초 유저 생성
 */
@Component
public class AppRunner implements ApplicationRunner {

    private final AccountService accountService;

    @Autowired
    public AppRunner(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public void run(ApplicationArguments args) {
        //사용자저장
        Account account1 = Account.builder()
                .userid("admin")
                .username("김민규")
                .password("123789")
                .role(AccountRole.ROLE_ADMIN)
                .insert_id("system")
                .insertDateTime(LocalDateTime.now())
                .build();

        Account account = accountService.findAccountByUsername(account1.getUsername());
        if(account == null){
            accountService.save(account1);
        }
    }

}
