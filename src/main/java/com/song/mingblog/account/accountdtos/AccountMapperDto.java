package com.song.mingblog.account.accountdtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Minkyu
 * Date : 2022-07-22
 * Time :
 * Remark : 유저 AccountMapperDto
 */
@Data
@NoArgsConstructor
public class AccountMapperDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userid;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @Builder
    public AccountMapperDto(String userid, String password, String userName) {
        this.userid = userid;
        this.password = password;
        this.userName = userName;
    }

}
