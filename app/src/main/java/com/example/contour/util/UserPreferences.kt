package com.example.contour.util

import android.content.Context
import android.content.SharedPreferences

object UserPreferences {
    private const val PREFS_NAME = "contour_prefs"

    private const val KEY_FULL_NAME = "full_name"
    private const val KEY_USERNAME = "username"
    private const val KEY_EMAIL = "email"
    private const val KEY_CURRENCY_CODE = "currency_code"
    private const val KEY_CURRENCY_SYMBOL = "currency_symbol"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private fun prefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveFullName(context: Context, name: String) {
        prefs(context).edit().putString(KEY_FULL_NAME, name).apply()
    }

    fun getFullName(context: Context): String {
        return prefs(context).getString(KEY_FULL_NAME, "") ?: ""
    }

    fun saveUsername(context: Context, username: String) {
        prefs(context).edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(context: Context): String {
        return prefs(context).getString(KEY_USERNAME, "") ?: ""
    }

    fun saveEmail(context: Context, email: String) {
        prefs(context).edit().putString(KEY_EMAIL, email).apply()
    }

    fun getEmail(context: Context): String {
        return prefs(context).getString(KEY_EMAIL, "") ?: ""
    }

    fun saveCurrency(context: Context, code: String, symbol: String) {
        prefs(context).edit()
            .putString(KEY_CURRENCY_CODE, code)
            .putString(KEY_CURRENCY_SYMBOL, symbol)
            .apply()
    }

    fun getCurrencyCode(context: Context): String {
        return prefs(context).getString(KEY_CURRENCY_CODE, "USD") ?: "USD"
    }

    fun getCurrencySymbol(context: Context): String {
        return prefs(context).getString(KEY_CURRENCY_SYMBOL, "$") ?: "$"
    }

    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        prefs(context).edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        return prefs(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getFirstName(context: Context): String {
        val full = getFullName(context)
        return if (full.contains(" ")) full.substringBefore(" ") else full
    }

    fun clearAll(context: Context) {
        prefs(context).edit().clear().apply()
    }
}
