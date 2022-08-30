package com.song.mingblog.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.mingblog.account.accountdtos.AccountMapperDto;
import com.song.mingblog.common.ResponseErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "ADMIN")
class AccountRestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("사용자 등록 성공 테스트")
    void accountSave1() throws Exception{

        // given
        AccountMapperDto request = AccountMapperDto.builder()
                .userid("testUser")
                .password("1234")
                .userName("testName")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/accountSave")
                .contentType(APPLICATION_JSON)
                .with(csrf()) // csrf토큰 설정
                .content(json)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
        .andDo(print());

        // than
        Account account = accountRepository.findByuserName(request.getUserName());
        assertEquals("testUser", account.getUserid());
        assertEquals("testName", account.getUserName());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    @DisplayName("사용자 수정 성공 테스트")
    void accountSave2() throws Exception{
        // given
        AccountMapperDto request = AccountMapperDto.builder()
                .userid("admin")
                .password("1234")
                .userName("adminUpdate")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/accountSave")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()) // csrf토큰 설정
                .content(json)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
                .andDo(MockMvcResultHandlers.print());

        // than
        Account account = accountRepository.findByuserName(request.getUserName());
        assertEquals("admin", account.getUserid());
        assertEquals("adminUpdate", account.getUserName());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    @DisplayName("사용자 아이디 미입력 실패 테스트(Null 포함)")
    void accountSave3() throws Exception{
        // given
        AccountMapperDto request = AccountMapperDto.builder()
                .userid("")
                .password("1234")
                .userName("test")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/accountSave")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()) // csrf토큰 설정
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.userid").value("아이디를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    @DisplayName("사용자 비밀번호 미입력 실패 테스트(Null 포함)")
    void accountSave4() throws Exception{
        // given
        AccountMapperDto request = AccountMapperDto.builder()
                .userid("test")
                .password("")
                .userName("test")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/accountSave")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()) // csrf토큰 설정
                .content(json)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.password").value("비밀번호를 입력해주세요."))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    @DisplayName("사용자 아이디 중복체크 성공 테스트")
    void saveOverlap1() throws Exception{

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account/saveOverlap")
                .contentType(MediaType.ALL)
                .with(csrf()) // csrf토큰 설정
                .param("userid","admin")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.err_code").value(ResponseErrorCode.CODE002.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.err_msg").value("해당 아이디는 "+ResponseErrorCode.CODE002.getDesc()))
                .andDo(MockMvcResultHandlers.print());
    }

}