package me.jussilemmetyinen.jussinetwork.bookmarks.persistence.dao;

import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenRepository, Long> {

    PasswordResetTokenRepository findByToken(String token);

    PasswordResetTokenRepository findByUser(User user);

    Stream<PasswordResetTokenRepository> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from PasswordResetTokenRepository t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
