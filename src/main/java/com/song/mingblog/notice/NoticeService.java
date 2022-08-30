package com.song.mingblog.notice;

import com.song.mingblog.notice.noticeDtos.NoticeDto;
import com.song.mingblog.notice.noticeDtos.NoticeReponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Notice save(NoticeDto noticeDto) {
        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .contents(noticeDto.getContents())
                .insert_id("system")
                .insert_date(LocalDateTime.now())
                .build();
        return noticeRepository.save(notice);
    }

    // 게시물 뷰 API
    public NoticeReponse view(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 글입니다."));

        return NoticeReponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .contents(notice.getContents())
                .build();
    }

}
