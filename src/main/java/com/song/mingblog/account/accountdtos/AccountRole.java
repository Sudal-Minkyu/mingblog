package com.song.mingblog.account.accountdtos;

/**
 * @author Minkyu
 * Date : 2022-07-15
 * Time :
 * Remark : 사용자 권한 구분
 */
public enum AccountRole {
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_USER("ROLE_USER", "사용자");

    private final String code;
    private final String desc;

    AccountRole(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }}


