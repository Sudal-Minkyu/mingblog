package com.song.mingblog.notice;

import lombok.Builder;
import lombok.Getter;

// 공지사항 제목글자제한 -> 생성자를 통해 해결방법 분리
@Getter
public class NoticeReponse {

    private final Long id;
    private final String title; // 글 제목(NotNull)
    private final String contents; // 글 제목(Null)

    @Builder
    public NoticeReponse(Long id, String title, String contents) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.contents = contents;
    }

}
