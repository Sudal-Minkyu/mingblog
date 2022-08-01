package com.song.mingblog.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value="/api/notice")
public class NoticeRestController {

    // 게시물 등록 API
    @PostMapping("/reg")
    public Map<String, String> apiNoticeReg(@RequestBody @Valid NoticeDto noticeDto){

        Map<String, String> maps = new HashMap<>();
        log.info("noticeDto.getTitle : "+ noticeDto.getTitle());
        log.info("noticeDto.getContents : "+ noticeDto.getContents());

        maps.put("title",noticeDto.getTitle());
        maps.put("contents",noticeDto.getContents());

        return maps;

    }

}
