package com.song.mingblog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Minkyu
 * Remark : Error컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/error")
public class ErrorController {

    // 인증되지 않은 사용자페이지
    @RequestMapping("error401")
    public String error401(){
        log.info("인증되지 않은 사용자페이지 입니다.");
        return "error/401";
    }

    // 권한없는 페이지
    @RequestMapping("error403")
    public String error403(){
        log.info("권한없는 페이지 입니다.");
        return "error/403";
    }

    // 존재하지 않은 페이지
    @RequestMapping("error404")
    public String error404(){
        log.info("존재하지 않은 페이지 입니다.");
        return "error/404";
    }

}
