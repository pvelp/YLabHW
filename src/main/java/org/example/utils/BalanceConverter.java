package org.example.utils;

public class BalanceConverter {
    public static String convertBalance(long balance) {
        return (balance / 100) + " рублей " + (balance % 100) + " копеек ";
    }
}
