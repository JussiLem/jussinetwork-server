package me.jussilemmetyinen.jussinetwork.bookmarks.repository;

import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
  Collection<Bookmark> findByAccountUsername(String username);
}
