package com.song.mingblog.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title; // 글 제목(NotNull)

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents; // 글 제목(Null)

}
