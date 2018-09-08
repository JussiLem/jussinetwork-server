package me.jussilemmetyinen.socialnetwork.bookmarks.repository;

import java.util.Optional;

import me.jussilemmetyinen.socialnetwork.bookmarks.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByUsername(String username);
}
