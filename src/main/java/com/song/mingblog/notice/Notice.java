package com.song.mingblog.notice;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Remark : 공지사항 게시물 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="mb_notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="md_title")
    private String title; // 글 제목(NotNull)

    @Lob
    @Column(name="md_contents")
    private String contents; // 글 제목(Null)

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="insert_date")
    private LocalDateTime insert_date;

    @Column(name="modify_id")
    private String modify_id;

    @Column(name="modify_date")
    private LocalDateTime modify_date;

    // @Builder
    // * 빌더의 장점
    // 1. 필요한 데이터만 설정할 수 있음
    // 2. 유연성을 확보할 수 있음
    // 3. 가독성을 높일 수 있음
    // 4. * 객체의 불변성을 확보할 수 있음
    @Builder
    public Notice(String title, String contents, String insert_id, LocalDateTime insert_date) {
        this.title = title;
        this.contents = contents;
        this.insert_id = insert_id;
        this.insert_date = insert_date;
    }

}
