package com.example.cattime.data.model

import android.text.TextUtils
import android.util.Patterns

data class User(
        val email: String,
        val password: String
) {
    companion object {
        fun verifyEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun verifyPassword(password: String): Boolean {
            return !TextUtils.isEmpty(password)
        }
    }
}