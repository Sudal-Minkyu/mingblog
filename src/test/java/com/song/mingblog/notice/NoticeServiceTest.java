package com.song.mingblog.notice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations="classpath:application-test.properties") // 테스트용 db 설정
@AutoConfigureMockMvc
@SpringBootTest
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
        Notice notice = noticeService.view(saveNotice.getId());

        // than
        assertEquals(1L, noticeRepository.count());
        assertEquals("good", notice.getTitle());
        assertEquals("bad", notice.getContents());
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
        Notice notice = noticeService.view(request.getId());

        // expected
        mockMvc.perform(get("/api/notice//view/{id}", notice.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}