package com.song.mingblog.notice;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Minkyu
 * Remark : 공지사항 게시물 엔티티
 */
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public Notice(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
