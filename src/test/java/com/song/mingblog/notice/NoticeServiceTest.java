package com.song.mingblog.notice;

import com.song.mingblog.notice.noticeDtos.NoticeDto;
import com.song.mingblog.notice.noticeDtos.NoticeReponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(roles = "ADMIN")
class NoticeServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository  noticeRepository;

    @BeforeEach
    void clean() {
        noticeRepository.deleteAll();
    }

    @Test
    @DisplayName("공지사항 글 작성테스트")
    public void noticeServiceSaveTest1(){
        // given
        NoticeDto request = NoticeDto.builder()
                .title("제목입니다.")
                .contents("내용입니다.")
                .build();

        // when
        noticeService.save(request);

        // than
        assertEquals(1L, noticeRepository.count());
        Notice notice = noticeRepository.findAll().get(0);
        assertEquals("제목입니다.", notice.getTitle());
        assertEquals("내용입니다.", notice.getContents());
    }

    @Test
    @DisplayName("공지사항 글 조회테스트")
    public void noticeServiceViewTest1(){
        // given
        NoticeDto request = NoticeDto.builder()
                .title("good")
                .contents("bad")
                .build();
        Notice saveNotice = noticeService.save(request);

        // when
        NoticeReponse noticeReponse = noticeService.view(saveNotice.getId());

        // than
        assertEquals(1L, noticeRepository.count());
        assertEquals("good", noticeReponse.getTitle());
        assertEquals("bad", noticeReponse.getContents());
    }

    @Test
    @DisplayName("공지사항 글 생성후 조회")
    public void noticeServiceViewTest2() throws Exception {
        // given
        Notice request = Notice.builder()
                .title("제목입니다.")
                .contents("내용입니다.")
                .insert_id("test")
                .insert_date(LocalDateTime.now())
                .build();
        noticeRepository.save(request);

        // when
        NoticeReponse noticeReponse = noticeService.view(request.getId());

        // expected
        mockMvc.perform(get("/api/notice//view/{id}", noticeReponse.getId())
                .with(csrf()) // csrf토큰 설정
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("공지사항 제목 글자제한 테스트")
    public void noticeServiceViewTest3() throws Exception {
        // given
        Notice request = Notice.builder()
                .title("12345678901234567")
                .contents("내용입니다.")
                .insert_id("test")
                .insert_date(LocalDateTime.now())
                .build();
        noticeRepository.save(request);

        // when
        NoticeReponse noticeReponse = noticeService.view(request.getId());

        // expected
        mockMvc.perform(get("/api/notice//view/{id}", noticeReponse.getId())
                .with(csrf()) // csrf토큰 설정
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // than
        assertEquals(1L, noticeRepository.count());
        assertEquals("1234567890", noticeReponse.getTitle());
        assertEquals("내용입니다.", noticeReponse.getContents());
    }


}