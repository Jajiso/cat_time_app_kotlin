package com.example.cattime.domain

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper {
    companion object {
        private const val PREF_FILE_NAME = "pop_up_pref_file"
        private const val PREF_KEY_USER_EMAIL = "user_email";
        private const val PREF_KEY_USER_PASSWORD = "user_password";

        private fun getSharedPreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        }

        private fun getPreferencesEditor(context: Context): SharedPreferences.Editor {
            return getSharedPreference(context).edit()
        }

        fun resetPreferences(context: Context) {
            getPreferencesEditor(context).clear().apply()
        }

        fun getUserEmail(context: Context): String {
            return getSharedPreference(context).getString(PREF_KEY_USER_EMAIL, "")!!
        }

        fun getUserPassword(context: Context): String {
            return getSharedPreference(context).getString(PREF_KEY_USER_PASSWORD, "")!!
        }

        fun setUserEmail(context: Context, email: String) {
            val editor = getPreferencesEditor(context)
            editor.putString(PREF_KEY_USER_EMAIL, email)
            editor.apply()
        }

        fun setUserPassword(context: Context, password: String) {
            val editor = getPreferencesEditor(context)
            editor.putString(PREF_KEY_USER_PASSWORD, password)
            editor.apply()
        }
    }
}