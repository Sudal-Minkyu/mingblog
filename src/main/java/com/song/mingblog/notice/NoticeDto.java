package com.song.mingblog.notice;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title; // 글 제목(NotNull)

    @Lob
    @NotBlank(message = "내용을 입력해주세요.")
    private String contents; // 글 제목(Null)

}
