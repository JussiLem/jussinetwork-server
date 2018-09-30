package me.jussilemmetyinen.jussinetwork.bookmarks.persistence.model;

import javax.persistence.*;
import java.util.Collection;
import org.jboss.aerogear.security.otp.api.Base32;

@Entity
@Table(name = "user_account")
public class User {

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  @Column(length = 60)
  private String password;

  private boolean enabled;

  private boolean isUsing2FA;

  private String secret;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  public User() {
    super();
    this.secret = Base32.random();
    this.enabled = false;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String username) {
    this.email = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(final Collection<Role> roles) {
    this.roles = roles;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isUsing2FA() {
    return isUsing2FA;
  }

  public void setUsing2FA(boolean isUsing2FA) {
    this.isUsing2FA = isUsing2FA;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + ((email == null) ? 0 : email.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    final User user = (User) object;
    return email.equals(user.email);
  }

  @Override
  public String toString() {
    return "User [id="
        + id
        + ", firstName="
        + firstName
        + ", lastName="
        + lastName
        + ", password="
        + password
        + ", enabled="
        + enabled
        + ", isUsing2FA"
        + isUsing2FA
        + ", secret="
        + secret
        + ", roles="
        + roles
        + "]";
  }
}
