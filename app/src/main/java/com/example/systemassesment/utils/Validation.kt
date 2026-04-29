package com.example.systemassesment.utils

import android.util.Patterns

object Validation {
    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.length in 8..15
}
