package com.song.mingblog.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByuserName(String useruame);

    @Query("SELECT a FROM Account a where a.userid = :userid")
    Optional<Account> findByUserId(@Param("userid")String userid);
}