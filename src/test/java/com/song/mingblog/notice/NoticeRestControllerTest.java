package com.song.mingblog.notice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "ADMIN")
class NoticeRestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoticeRepository noticeRepository;

    @BeforeEach
    void clean() {
        noticeRepository.deleteAll();
    }

    @Test
    @DisplayName("공시사항 게시물 등록 테스트")
    void apiNoticeRegTest1() throws Exception{
        // given
        Notice request = Notice.builder()
                .title("제목입니다.")
                .contents("내용입니다.")
                .insert_id("test")
                .insert_date(LocalDateTime.now())
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/notice/reg")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()) // csrf토큰 설정
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("공시사항 제목 필수입력 체크 테스트")
    void apiNoticeRegTest2() throws Exception{
        // given
        Notice request = Notice.builder()
                .contents("내용입니다.")
                .insert_id("test")
                .insert_date(LocalDateTime.now())
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/notice/reg")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()) // csrf토큰 설정
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("공시사항 저장 테스트")
    void apiNoticeRegTest3() throws Exception{
        // given
        Notice request = Notice.builder()
                .title("테스트 제목입니다.")
                .contents("내용입니다.")
                .insert_id("test")
                .insert_date(LocalDateTime.now())
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/notice/reg")
                        .contentType(APPLICATION_JSON)
                        .with(csrf()) // csrf토큰 설정
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // than
        assertEquals(1L, noticeRepository.count());

        Notice notice = noticeRepository.findAll().get(0);
        assertEquals("테스트 제목입니다.", notice.getTitle());
        assertEquals("내용입니다.", notice.getContents());
    }


}