package me.jussilemmetyinen.jussinetwork.bookmarks.security;

public interface ISecurityUserService {
    String validatePasswordResetToken(long id, String token);
}
