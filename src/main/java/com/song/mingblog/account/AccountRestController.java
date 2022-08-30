package com.song.mingblog.account;

import com.song.mingblog.account.accountdtos.AccountMapperDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author Minkyu
 * Date : 2022-07-22
 * Time :
 * Remark : Account RestController 관리자권한
 */
@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountRestController {

    private final AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "사용자등록", notes = "관리자가 사용자를 등록한다.")
    @ApiImplicitParams({@ApiImplicitParam(name="insert_id", value = "insert_id", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/accountSave")
    public ResponseEntity<Map<String,Object>> save(@RequestBody @Valid AccountMapperDto accountMapperDto, HttpServletRequest request){
        return accountService.accountSave(accountMapperDto, request);
    }

    @ApiOperation(value = "사용자 아이디 중복체크", notes = "아이디 중복체크를 한다.")
    @ApiImplicitParams({@ApiImplicitParam(name="insert_id", value = "insert_id", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/saveOverlap")
    public ResponseEntity<Map<String,Object>> saveOverlap(@RequestParam("userid") String userid){
        return accountService.saveOverlap(userid);
    }

}
