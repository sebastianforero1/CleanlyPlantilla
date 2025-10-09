package com.xacarana.myapplication.auth

import android.content.Context
import android.content.SharedPreferences

/**
 * Autenticación local sin Firebase.
 * Guarda: nombre, email (gmail), password (hash simple opcional).
 */
class AuthService(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    val isLoggedIn: Boolean
        get() = prefs.getBoolean("logged_in", false)

    fun currentEmail(): String? = prefs.getString("email", null)
    fun currentName(): String? = prefs.getString("name", null)

    fun login(email: String, password: String): Boolean {
        val savedEmail = prefs.getString("email", null)
        val savedPass = prefs.getString("password", null)
        val ok = (email.equals(savedEmail, ignoreCase = true) && password == savedPass)
        if (ok) prefs.edit().putBoolean("logged_in", true).apply()
        return ok
    }

    fun signUp(name: String, email: String, password: String): Result<Unit> {
        // Validar que sea Gmail
        val isValidGmail = Regex("^[A-Za-z0-9._%+-]+@gmail\\.com$").matches(email)
        if (!isValidGmail) return Result.failure(IllegalArgumentException("El correo debe ser @gmail.com"))

        prefs.edit()
            .putString("name", name.trim())
            .putString("email", email.trim())
            .putString("password", password)
            .putBoolean("logged_in", true)
            .apply()
        return Result.success(Unit)
    }

    fun logout() {
        prefs.edit().putBoolean("logged_in", false).apply()
    }
}
