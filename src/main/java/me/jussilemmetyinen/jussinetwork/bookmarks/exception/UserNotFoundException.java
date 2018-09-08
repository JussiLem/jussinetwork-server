package me.jussilemmetyinen.jussinetwork.bookmarks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 9095620940763053100L;

    public UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}
