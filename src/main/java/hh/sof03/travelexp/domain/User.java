package hh.sof03.travelexp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity(name="users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    @Size(min=4, max=30, message = "Vähintään 4 merkkiä.")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min=4, max=1000, message = "Vähintään 4 merkkiä.")
    //@JsonIgnore rajoitettu nyt pääsy vain adminille, joten voi nähdä.
    private String passwordHash;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name="role", nullable = false)
    private String role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

      public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String passwordHash, String email, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
    }

    public User() {

    }

  


}
