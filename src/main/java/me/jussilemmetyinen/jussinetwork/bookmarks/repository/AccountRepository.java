package me.jussilemmetyinen.jussinetwork.bookmarks.repository;

import java.util.Optional;

import me.jussilemmetyinen.jussinetwork.bookmarks.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByUsername(String username);
}
