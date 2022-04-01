package com.multi.trivia.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.multi.trivia.data.model.User
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val preferences: SharedPreferences
) {

    private val gson = Gson()

    fun getSession(key: String): User {
        val json = preferences.getString(key, "")
        return gson.fromJson(json, User::class.java)
    }

    fun setSession(key: String, value: User) {
        val json = gson.toJson(value)
        preferences.edit().putString(key, json).apply()
    }
}