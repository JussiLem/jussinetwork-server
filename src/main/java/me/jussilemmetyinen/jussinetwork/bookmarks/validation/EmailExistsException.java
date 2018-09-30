package me.jussilemmetyinen.jussinetwork.bookmarks.validation;

@SuppressWarnings("serial")
public class EmailExistsException extends Exception {

  public EmailExistsException(final String message) {
    super(message);
  }
}
