package com.song.mingblog.account;

import com.song.mingblog.account.accountdtos.AccountRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(unique = true,name="user_id")
    private String userid;

    @Column(name="user_password")
    private String password;

    @Column(name="user_name")
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private AccountRole role;

    @Column(name="insert_id")
    private String insert_id;

    @Column(name="insert_date")
    private LocalDateTime insert_date;

}
