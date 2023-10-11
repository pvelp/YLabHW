package org.example.entity;

import org.example.utils.PasswordEncryption;

import java.util.UUID;

public class Player {
    private final UUID id;
    private String username;
    private String password;
    private long balance;

    private boolean isAuthenticated;

    public Player(UUID id, String username, String password, long balance, boolean isAuthenticated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.isAuthenticated = isAuthenticated;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordEncryption.encryptPassword(password);
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void addBalance(long amount) {
        this.balance += amount;
    }

    public void subBalance(long amount) {
        this.balance -= amount;
    }

    public final boolean isPasswordValid(String pass) {
        return PasswordEncryption.checkPassword(pass, this.password);
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", isAuthenticated=" + isAuthenticated +
                '}';
    }
}
