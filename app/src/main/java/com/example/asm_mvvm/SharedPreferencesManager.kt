package com.example.asm_mvvm

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREFS_NAME = "user_prefs"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    fun getEmail(): String? {
        return sharedPreferences.getString("email", null)
    }
    fun savePassword(pass: String) {
        val editor = sharedPreferences.edit()
        editor.putString("pass", pass)
        editor.apply()
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("pass", null)
    }
}