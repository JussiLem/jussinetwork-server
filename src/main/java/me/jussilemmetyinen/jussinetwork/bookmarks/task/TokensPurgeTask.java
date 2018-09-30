package me.jussilemmetyinen.jussinetwork.bookmarks.task;

import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.dao.PasswordResetTokenRepository;
import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.dao.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class TokensPurgeTask {

  final VerificationTokenRepository tokenRepository;

  final PasswordResetTokenRepository passwordResetTokenRepository;

  @Autowired
  public TokensPurgeTask(
      VerificationTokenRepository tokenRepository,
      PasswordResetTokenRepository passwordResetTokenRepository) {
    this.tokenRepository = tokenRepository;
    this.passwordResetTokenRepository = passwordResetTokenRepository;
  }

  @Scheduled(cron = "${purge.cron.expression}")
  public void purgeExpired() {
    Date now = Date.from(Instant.now());

    passwordResetTokenRepository.deleteAllExpiredSince(now);
    tokenRepository.deleteAllExpiredSince(now);
  }
}
