package com.song.mingblog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ManController {

    @RequestMapping(value="/")
    public String index(){
        return "main/index";
    }

    @RequestMapping(value="/main")
    public String main(){
        return "main/mainpage";
    }

    // 로그인페이지
    @RequestMapping("/login")
    public String login(){
        return "main/login";
    }

}
