package org.example.entity;

import java.util.UUID;

public class Player {
    private final UUID id;
    private String password;
    private double balance;

    public Player(UUID id, String password, double balance) {
        this.id = id;
        this.password = password;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
