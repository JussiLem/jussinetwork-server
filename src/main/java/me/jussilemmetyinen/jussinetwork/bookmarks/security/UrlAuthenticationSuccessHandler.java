package me.jussilemmetyinen.jussinetwork.bookmarks.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.flogger.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("UrlAuthenticationSuccessHandler")
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private static final FluentLogger LOGGER = FluentLogger.forEnclosingClass();

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  final ActiveUserStore activeUserStore;

  @Autowired
  public UrlAuthenticationSuccessHandler(ActiveUserStore activeUserStore) {
    this.activeUserStore = activeUserStore;
  }

  @Override
  public void onAuthenticationSuccess(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Authentication authentication)
      throws IOException {
    handle(request, response, authentication);
    final HttpSession session = request.getSession(false);
    if (session != null) {
      session.setMaxInactiveInterval(30 * 60);
      LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
      session.setAttribute("user", user);
    }
    clearAuthenticationAttributes(request);
  }

  protected void handle(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Authentication authentication)
      throws IOException {
    final String targetUrl = determineTargetUrl(authentication);

    if (response.isCommitted()) {
      LOGGER.atSevere().log(
          "Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }
    redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(final Authentication authentication) {
    boolean isUser = false;
    boolean isAdmin = false;
    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    for (final GrantedAuthority grantedAuthority : authorities) {
      if (grantedAuthority.getAuthority().equals("READ_PRIVILEGE")) {
        isUser = true;
      } else if (grantedAuthority.getAuthority().equals("WRITE_PRIVILEGE")) {
        isAdmin = true;
        isUser = false;
        break;
      }
    }
    if (isUser) {
      return "/homepage.html?user=" + authentication.getName();
    } else if (isAdmin) {
      return "/console.html";
    } else {
      throw new IllegalStateException();
    }
  }

  protected void clearAuthenticationAttributes(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);
    if (session == null) {
      return;
    }
    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

  public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
    this.redirectStrategy = redirectStrategy;
  }

  protected RedirectStrategy getRedirectStrategy() {
    return redirectStrategy;
  }
}
