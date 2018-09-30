package me.jussilemmetyinen.jussinetwork.bookmarks.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccessEventListener
    implements ApplicationListener<AuthenticationSuccessEvent> {
  private final HttpServletRequest request;

  private final LoginAttemptService loginAttemptService;

  @Autowired
  public AuthenticationSuccessEventListener(
      HttpServletRequest request, LoginAttemptService loginAttemptService) {
    this.request = request;
    this.loginAttemptService = loginAttemptService;
  }

  @Override
  public void onApplicationEvent(AuthenticationSuccessEvent e) {
    // final WebAuthenticationDetails auth = (WebAuthenticationDetails)
    // e.getAuthentication().getDetails();
    // if (auth != null) {
    // loginAttemptService.loginSucceeded(auth.getRemoteAddress());
    // }
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      loginAttemptService.loginSucceeded(request.getRemoteAddr());
    } else {
      loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
    }
  }
}
