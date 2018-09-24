package me.jussilemmetyinen.jussinetwork.bookmarks.controller;

import me.jussilemmetyinen.jussinetwork.bookmarks.security.ActiveUserStore;
import me.jussilemmetyinen.jussinetwork.bookmarks.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    IUserService userService;

    @GetMapping(value = "/loggedUsers")
    public String getLoggedUsers(final Locale locale, final Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }

    @GetMapping(value = "/loggedUsersFromSessionRegistry")
    public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
        model.addAttribute("users", userService.getUsersFromSessionRegistry());
        return "users";
    }
}
