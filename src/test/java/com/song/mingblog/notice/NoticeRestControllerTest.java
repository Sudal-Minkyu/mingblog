package com.song.mingblog.notice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class NoticeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("공시사항 게시물 등록 테스트")
    void apiNoticeRegTest1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notice/reg")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\" : \"제목입니다.\", \"contents\" : \"내용입니다.\"}")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contents").value("내용입니다."))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("공시사항 제목 필수입력 체크 테스트")
    void apiNoticeRegTest2() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notice/reg")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\" : \"\", \"contents\" : \"내용입니다.\"}")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());
    }

}