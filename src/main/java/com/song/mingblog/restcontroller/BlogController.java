package com.song.mingblog.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class BlogController {

    // Hello World! 출력 API
    @GetMapping("/get")
    public String apiGet(){
        log.info("헬로우 월드에 진입!");
        return "Hello World!";
    }

}
