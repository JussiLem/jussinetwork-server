package me.jussilemmetyinen.jussinetwork.bookmarks.registration;
import java.util.Locale;

import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.domain.User;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final String appUrl;
    private final Locale locale;
    private final User user;
    /**
     * Create a new ApplicationEvent.
     *
     * @param user the object on which the event initially occurred (never {@code null})
     */
    public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() { return appUrl; }

    public Locale getLocale() { return locale; }

    public User getUser() { return user; }
}
