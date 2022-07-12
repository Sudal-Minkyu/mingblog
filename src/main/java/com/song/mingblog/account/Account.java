package com.song.mingblog.account;

import lombok.*;

import javax.persistence.*;

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
    private String username;

}
