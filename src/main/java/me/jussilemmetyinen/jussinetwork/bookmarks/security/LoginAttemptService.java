package me.jussilemmetyinen.jussinetwork.bookmarks.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

  private LoadingCache<String, Integer> attemptsCache;

  public LoginAttemptService() {
    super();
    attemptsCache =
        CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(
                new CacheLoader<>() {

                  @Override
                  public Integer load(String key) throws Exception {
                    return 0;
                  }
                });
  }

  //

  public void loginSucceeded(final String key) {
    attemptsCache.invalidate(key);
  }

  public void loginFailed(final String key) {
    var attempts = 0;
    try {
      attempts = attemptsCache.get(key);
    } catch (final ExecutionException e) {
    }
    attempts++;
    attemptsCache.put(key, attempts);
  }

  public boolean isBlocked(final String key) {
    try {
      int MAX_ATTEMPT = 10;
      return attemptsCache.get(key) >= MAX_ATTEMPT;
    } catch (final ExecutionException e) {
      return false;
    }
  }
}
