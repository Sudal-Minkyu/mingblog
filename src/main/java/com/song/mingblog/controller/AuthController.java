package com.song.mingblog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class AuthController {

    // 로그인
    @RequestMapping(value="/auth")
    public String auth(){
        return "main/index";
    }

    // 로그아웃
    @RequestMapping("/logout")
    public String logout(){
        return "main/login";
    }

}
