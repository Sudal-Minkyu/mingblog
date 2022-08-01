package com.song.mingblog.notice;

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

    public void save(NoticeDto noticeDto) {
        Notice notice = new Notice(noticeDto.getTitle(), noticeDto.getContents());
        notice.setInsert_id("system");
        notice.setInsert_date(LocalDateTime.now());
        noticeRepository.save(notice);
    }

}
