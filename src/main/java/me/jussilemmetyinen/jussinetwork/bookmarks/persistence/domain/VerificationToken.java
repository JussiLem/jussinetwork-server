package me.jussilemmetyinen.jussinetwork.bookmarks.persistence.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name =
    "FK_VERIFY_USER"))
    private User user;

    private Date expiryDate;

    public VerificationToken() { super(); }

    public VerificationToken(final String token) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public Long getId() { return id; }

    public String getToken() { return token; }

    public void setToken(final String token) { this.token = token; }

    public User getUser() { return user; }

    public void setUser(final User user) { this.user = user; }

    public Date getExpiryDate() {return expiryDate; }

    public void setExpiryDate(final Date expiryDate) { this.expiryDate = expiryDate; }

    private Date calculateExpiryDate(int expiration) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(calendar.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    //

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VerificationToken other = (VerificationToken) obj;
        if (expiryDate == null) {
            if (other.expiryDate != null) {
                return false;
            }
        } else if (!expiryDate.equals(other.expiryDate)) {
            return false;
        }
        if (token == null) {
            if (other.token != null) {
                return false;
            }
        } else if (!token.equals(other.token)) {
            return false;
        }
        if (user == null) {
            return other.user == null;
        } else return user.equals(other.user);
    }

    @Override
    public String toString() {
        return "Token [String=" + token + "]" + "[Expires" + expiryDate + "]";
    }
}
