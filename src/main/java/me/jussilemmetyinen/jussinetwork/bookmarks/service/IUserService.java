package me.jussilemmetyinen.jussinetwork.bookmarks.service;

import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.domain.PasswordResetToken;
import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.domain.User;
import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.domain.VerificationToken;
import me.jussilemmetyinen.jussinetwork.bookmarks.exception.UserAlreadyExistException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    User registerNewUserAccount() throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkifValidOldPassword(User user, String password);

    String validateVerificationToken (String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

}
