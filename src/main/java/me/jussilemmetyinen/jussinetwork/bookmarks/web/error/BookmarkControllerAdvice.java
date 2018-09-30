package me.jussilemmetyinen.jussinetwork.bookmarks.web.error;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class BookmarkControllerAdvice {

  @ResponseBody
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  VndErrors userNotFoundExceptionHandler(UserNotFoundException ex) {
    return new VndErrors("error", ex.getMessage());
  }
}
