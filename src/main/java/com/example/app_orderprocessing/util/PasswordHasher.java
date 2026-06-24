package com.example.app_orderprocessing.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hash(String password) {
        String salt = BCrypt.gensalt();
        String result = BCrypt.hashpw(password, salt);

        return result;
    }

    public static boolean check(String password, String savedPassword) {
        boolean result = BCrypt.checkpw(password, savedPassword);

        return result;
    }
}