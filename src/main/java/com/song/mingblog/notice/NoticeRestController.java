package com.song.mingblog.notice;

import com.song.mingblog.notice.noticeDtos.NoticeDto;
import com.song.mingblog.notice.noticeDtos.NoticeReponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public void apiNoticeReg(@RequestBody @Valid NoticeDto noticeDto){
        noticeService.save(noticeDto);
    }

    // 게시물 뷰 API
    @GetMapping("/view/{id}")
    public NoticeReponse apiNoticeView(@PathVariable(value = "id") Long id){
        return noticeService.view(id);
    }


}
