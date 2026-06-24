package com.example.app_orderprocessing.util;

public class PasswordValidator {

    public static boolean isValid(String password) {
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$";

        boolean result = password.matches(regex);
        return result;
    }
}