package com.example.movieshare.utils;

import static com.example.movieshare.constants.AuthConstants.*;

import com.google.android.material.textfield.TextInputEditText;

public class UserUtils {
    public static void setErrorIfFirstNameIsInvalid(TextInputEditText name) {
        if (!InputValidator.isFirstNameValid(name.getText())) {
            name.setError(REGISTER_INVALID_NAME);
        } else {
            name.setError(null);
        }
    }

    public static void setErrorIfLastNameIsInvalid(TextInputEditText name) {
        if (!InputValidator.isLastNameValid(name.getText())) {
            name.setError(REGISTER_INVALID_NAME);
        } else {
            name.setError(null);
        }
    }

    public static boolean setErrorIfEmailIsInvalid(TextInputEditText email) {
        if (!InputValidator.isEmailValid(email.getText())) {
            email.setError(REGISTER_INVALID_EMAIL);
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public static boolean setErrorIfPasswordIsInvalid(TextInputEditText password) {
        if (!InputValidator.isPasswordValid(password.getText())) {
            password.setError(REGISTER_INVALID_PASSWORD);
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}
