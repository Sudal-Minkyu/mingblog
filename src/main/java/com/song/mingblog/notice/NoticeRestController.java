package com.song.mingblog.notice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Map apiNoticeReg(@RequestBody @Valid NoticeDto noticeDto){
        Notice saveNotice = noticeService.save(noticeDto);
//        Map<String, String> maps = new HashMap<>();
//        log.info("noticeDto.getTitle : "+ noticeDto.getTitle());
//        log.info("noticeDto.getContents : "+ noticeDto.getContents());
//
//        maps.put("title",noticeDto.getTitle());
//        maps.put("contents",noticeDto.getContents());

        // 저장된 값의 ID를 유연하게 전달
        return Map.of("id", saveNotice.getId());
    }

    // 게시물 뷰 API
    @GetMapping("/view/{id}")
    public Notice apiNoticeView(@PathVariable(value = "id") Long id){
        Notice notice = noticeService.view(id);
        return notice;
    }


}
