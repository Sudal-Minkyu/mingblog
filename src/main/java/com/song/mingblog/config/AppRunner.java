package com.song.mingblog.config;

import com.song.mingblog.account.Account;
import com.song.mingblog.account.AccountService;
import com.song.mingblog.account.accountdtos.AccountRole;
import org.springframework.beans.factory.annotation.Autowired;
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
        Account saveAccount = Account.builder()
                .userid("admin")
                .userName("김민규")
                .password("123789")
                .role(AccountRole.ROLE_ADMIN)
                .insert_id("system")
                .insert_date(LocalDateTime.now())
                .build();

        Account account = accountService.findByuserName(saveAccount.getUserName());
        if(account == null){
            accountService.save(saveAccount);
        }
    }

}
