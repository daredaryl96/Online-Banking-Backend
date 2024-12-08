package org.example.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID") // Maps to UserID in the DB
    private Long id;


    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Password", nullable = false, unique = true)
    private String password;


    @Column(name = "fullname", nullable = false)
    private String full_name;

    @Column(name = "Email", unique = true, nullable = false) // Maps to Email in the DB
    private String email;

    @Column(name = "phonenumber", nullable = false) // Maps to PhoneNumber in the DB
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Account> accounts;

//    // No-args constructor (required by JPA)
//    public User() {}
//
//    // All-args constructor
//    public User(Long id, String fullName, String email, String phoneNumber) {
//        this.id = id;
//        this.full_name = fullName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return full_name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setFullName(String fullName) {
        this.full_name = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
