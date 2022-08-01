package com.song.mingblog.notice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
class NoticeRestControllerTest {

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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notice/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"제목입니다.\", \"contents\" : \"내용입니다.\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목입니다."))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.contents").value("내용입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("공시사항 제목 필수입력 체크 테스트")
    void apiNoticeRegTest2() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notice/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : null, \"contents\" : \"내용입니다.\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("공시사항 저장 테스트")
    void apiNoticeRegTest3() throws Exception{
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notice/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\" : \"테스트 제목입니다.\", \"contents\" : \"내용입니다.\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // than
        Assertions.assertEquals(1L, noticeRepository.count());

        Notice notice = noticeRepository.findAll().get(0);
        Assertions.assertEquals("테스트 제목입니다.", notice.getTitle());
        Assertions.assertEquals("내용입니다.", notice.getContents());
    }


}