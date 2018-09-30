package me.jussilemmetyinen.jussinetwork.bookmarks.service;

import me.jussilemmetyinen.jussinetwork.bookmarks.web.dto.UserDto;
import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.model.PasswordResetToken;
import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.model.User;
import me.jussilemmetyinen.jussinetwork.bookmarks.persistence.model.VerificationToken;
import me.jussilemmetyinen.jussinetwork.bookmarks.web.error.UserAlreadyExistException;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService {

  User registerNewUserAccount(@Valid UserDto accountDto) throws UserAlreadyExistException;

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

  boolean checkIfValidOldPassword(User user, String password);

  String validateVerificationToken(String token);

  String generateQRUrl(User user) throws UnsupportedEncodingException;

  User updateUser2FA(boolean use2FA);

  List<String> getUsersFromSessionRegistry();
}
