package com.xacarana.myapplication.auth

import android.content.Context
import android.content.SharedPreferences

/**
 * Servicio de autenticación local.
 * Guarda usuarios registrados en SharedPreferences.
 */
class AuthService(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    // Usuario actualmente logueado
    val userEmail: String?
        get() = prefs.getString("current_user", null)

    val isLoggedIn: Boolean
        get() = userEmail != null

    /**
     * Registrar nuevo usuario (email + pass + nombre)
     */
    suspend fun signup(email: String, password: String, name: String): Result<Unit> {
        val users = prefs.getStringSet("users", mutableSetOf())!!.toMutableSet()
        if (users.any { it.startsWith("$email|") }) {
            return Result.failure(Exception("El usuario ya existe"))
        }
        users.add("$email|$password|$name")
        prefs.edit().putStringSet("users", users).apply()
        prefs.edit().putString("current_user", email).apply()
        return Result.success(Unit)
    }

    /**
     * Iniciar sesión con usuario ya registrado
     */
    suspend fun login(email: String, password: String): Result<Unit> {
        val users = prefs.getStringSet("users", emptySet()) ?: emptySet()
        val found = users.firstOrNull { it.startsWith("$email|") }
        return if (found != null && found.split("|")[1] == password) {
            prefs.edit().putString("current_user", email).apply()
            Result.success(Unit)
        } else {
            Result.failure(Exception("Credenciales incorrectas"))
        }
    }

    /**
     * Cerrar sesión
     */
    fun logout() {
        prefs.edit().remove("current_user").apply()
    }
}
