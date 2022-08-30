package com.song.mingblog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Minkyu
 * Date : 2022-07-22
 * Time :
 * Remark : AdminController 권한 : 관리자
 */
@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    // 사용자등록 화면이동
    @RequestMapping("/userreg")
    public String main(){
        return "admin/userreg";
    }


}
