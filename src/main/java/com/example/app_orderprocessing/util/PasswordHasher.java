package com.example.app_orderprocessing.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hash(String password) {
        String hashPassword;

        hashPassword = BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );

        return hashPassword;
    }

    public static boolean check(
            String password,
            String hashPassword
    ) {
        boolean result;

        result = BCrypt.checkpw(
                password,
                hashPassword
        );

        return result;
    }
}