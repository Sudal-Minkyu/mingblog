package com.song.mingblog.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value="/api/notice")
public class NoticeRestController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeRestController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 게시물 등록 API
    @PostMapping("/reg")
    public Map<String, String> apiNoticeReg(@RequestBody @Valid NoticeDto noticeDto){
        noticeService.save(noticeDto);
//        Map<String, String> maps = new HashMap<>();
//        log.info("noticeDto.getTitle : "+ noticeDto.getTitle());
//        log.info("noticeDto.getContents : "+ noticeDto.getContents());
//
//        maps.put("title",noticeDto.getTitle());
//        maps.put("contents",noticeDto.getContents());

        return Map.of();

    }

}
