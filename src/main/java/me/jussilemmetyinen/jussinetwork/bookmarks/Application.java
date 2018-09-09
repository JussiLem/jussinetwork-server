package me.jussilemmetyinen.jussinetwork.bookmarks;

import java.util.Arrays;

import me.jussilemmetyinen.jussinetwork.bookmarks.domain.Account;
import me.jussilemmetyinen.jussinetwork.bookmarks.domain.Bookmark;
import me.jussilemmetyinen.jussinetwork.bookmarks.repository.AccountRepository;
import me.jussilemmetyinen.jussinetwork.bookmarks.repository.BookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           BookmarkRepository bookmarkRepository) {
        return args ->
                Arrays.asList("jhoeller","dsyer","pwebb","ogierke","rwinch","mfisher","mpollack","jlong")
                        .forEach(username -> {
                            Account account = accountRepository.save(new Account(username, "password"));
                            bookmarkRepository.save(new Bookmark(account, "http://localhost:8080/1/" +
                                    username, "A description"));
                            bookmarkRepository.save(new Bookmark(account, "http://localhost:8080/2/" +
                                    username, "A description"));
                        });
    }

}
