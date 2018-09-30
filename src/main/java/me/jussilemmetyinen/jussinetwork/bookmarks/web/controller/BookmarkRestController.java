package me.jussilemmetyinen.jussinetwork.bookmarks.web.controller;

import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.model.Bookmark;
import me.jussilemmetyinen.jussinetwork.bookmarks.web.error.BookmarkNotFoundException;
import me.jussilemmetyinen.jussinetwork.bookmarks.web.error.UserNotFoundException;
import me.jussilemmetyinen.jussinetwork.bookmarks.repository.AccountRepository;
import me.jussilemmetyinen.jussinetwork.bookmarks.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/bookmarks/{userId}")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
class BookmarkRestController {

  private final BookmarkRepository bookmarkRepository;
  private final AccountRepository accountRepository;

  @Autowired
  BookmarkRestController(
      BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
    this.bookmarkRepository = bookmarkRepository;
    this.accountRepository = accountRepository;
  }

  @GetMapping
  Collection<Bookmark> readBookmarks(@PathVariable String userId) {
    this.validateUser(userId);

    return this.bookmarkRepository.findByAccountUsername(userId);
  }

  @PostMapping
  ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
    this.validateUser(userId);

    return this.accountRepository
        .findByUsername(userId)
        .map(
            account -> {
              Bookmark result =
                  this.bookmarkRepository.save(
                      new Bookmark(account, input.getUri(), input.getDescription()));

              URI location =
                  ServletUriComponentsBuilder.fromCurrentRequest()
                      .path("/{id}")
                      .buildAndExpand(result.getId())
                      .toUri();

              return ResponseEntity.created(location).build();
            })
        .orElse(ResponseEntity.noContent().build());
  }

  @GetMapping("/{bookmarkId}")
  Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
    this.validateUser(userId);

    return this.bookmarkRepository
        .findById(bookmarkId)
        .orElseThrow(() -> new BookmarkNotFoundException(bookmarkId));
  }

  /**
   * Verify the {@literal userId} exists.
   *
   * @param userId
   */
  private void validateUser(String userId) {
    this.accountRepository
        .findByUsername(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }
}
