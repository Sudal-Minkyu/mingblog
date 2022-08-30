package com.song.mingblog.notice.noticeDtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NoticeDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title; // 글 제목(NotNull)

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents; // 글 제목(Null)

    @Builder
    public NoticeDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
