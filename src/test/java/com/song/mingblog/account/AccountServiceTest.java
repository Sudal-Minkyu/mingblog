package com.song.mingblog.account;

import com.song.mingblog.account.accountdtos.AccountReponse;
import com.song.mingblog.account.accountdtos.AccountRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("사용자 저장 성공 테스트")
    void save1() {

        // given
        Account request = Account.builder()
                .userid("testServiceUser")
                .password("1234")
                .userName("testServiceName")
                .role(AccountRole.ROLE_USER)
                .insert_id("system")
                .insert_date(LocalDateTime.now())
                .build();

        // when
        accountService.save(request);

        // than
        Account account = accountRepository.findByuserName(request.getUserName());
        assertEquals("testServiceUser", account.getUserid());
        assertEquals("testServiceName", account.getUserName());
    }

    @Test
    @DisplayName("사용자명 조회 성공 테스트")
    void findByUserName1() {
        // when
        Account account = accountService.findByuserName("testServiceName");

        // than
        assertEquals("testServiceUser", account.getUserid());
        assertEquals("testServiceName", account.getUserName());
    }

    @Test
    @DisplayName("사용자ID로 조회 성공 테스트")
    void findByUserId1() {
        // when
        AccountReponse accountReponse = accountService.findByUserId("testServiceUser");

        // than
        assertEquals("testServiceUser", accountReponse.getUserid());
        assertEquals("testServiceName", accountReponse.getUserName());
    }





}