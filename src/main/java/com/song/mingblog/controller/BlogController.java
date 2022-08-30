package com.song.mingblog.controller;

import com.song.mingblog.account.AccountService;
import com.song.mingblog.account.accountdtos.AccountReponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class BlogController {

    private final AccountService accountService;

    @Autowired
    public BlogController(AccountService accountService) {
        this.accountService = accountService;
    }

    //메인화면
    @RequestMapping(value = {"/", "/main"})
    public String main(){
        return "main";
    }

    // 로그인 성공시 세션에 데이터 정보저장
    @RequestMapping("/loginActive")
    public String loginActive(HttpServletRequest request){

        //Security 로그인정보가져와서 세션에 저장
        HttpSession session = request.getSession();

        AccountReponse accountReponse = accountService.findByUserId(request.getUserPrincipal().getName());
        if (accountReponse != null) {

            //userid 세션저장
            session.setAttribute("userId", accountReponse.getUserid());
            session.setAttribute("userName", accountReponse.getUserName());
            session.setAttribute("role", accountReponse.getRole().getCode());

            log.info("로그인 아이디 : "+session.getAttribute("userId"));
            log.info("사용자명 : "+session.getAttribute("userName"));
            log.info("사용자권한 : "+session.getAttribute("role"));
        }

        return "redirect:/main";
    }

    // 로그인 api
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);
        return "login";
    }

}

