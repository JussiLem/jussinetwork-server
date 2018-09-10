package me.jussilemmetyinen.jussinetwork.bookmarks.exception;

public final class UserAlreadyExistException extends RuntimeException {
    public static final long serialVersionUID = 7930422537495853313L;

    public UserAlreadyExistException() { super(); }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final String message) {super(message);}

    public UserAlreadyExistException(final Throwable cause) {super(cause);}
}
