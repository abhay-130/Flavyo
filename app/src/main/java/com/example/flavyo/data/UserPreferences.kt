package com.example.flavyo.data

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FlavyoPrefs", Context.MODE_PRIVATE)

    // Inside your UserPreferences class
    var userRole: String?
        get() = sharedPreferences.getString("user_role", "user")
        set(value) = sharedPreferences.edit().putString("user_role", value).apply()

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean("isLoggedIn", false)
        set(value) = sharedPreferences.edit().putBoolean("isLoggedIn", value).apply()

    var userName: String?
        get() = sharedPreferences.getString("userName", null)
        set(value) = sharedPreferences.edit().putString("userName", value).apply()

    var userEmail: String?
        get() = sharedPreferences.getString("userEmail", null)
        set(value) = sharedPreferences.edit().putString("userEmail", value).apply()

    var userAddress: String?
        get() = sharedPreferences.getString("userAddress", null)
        set(value) = sharedPreferences.edit().putString("userAddress", value).apply()

    var hasNewNotification: Boolean
        get() = sharedPreferences.getBoolean("has_new_notif", false)
        set(value) = sharedPreferences.edit().putBoolean("has_new_notif", value).apply()

    var lastKnownNotificationCount: Int
        get() = sharedPreferences.getInt("lastKnownNotificationCount", 0)
        set(value) = sharedPreferences.edit().putInt("lastKnownNotificationCount", value).apply()

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}
