package org.example.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncryption {
    public static String encryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean checkPassword(String password, String encryptedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), encryptedPassword).verified;
    }

}
