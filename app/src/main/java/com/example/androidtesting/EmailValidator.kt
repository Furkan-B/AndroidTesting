package com.example.androidtesting

import androidx.core.util.PatternsCompat

class EmailValidator {

    fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }
}