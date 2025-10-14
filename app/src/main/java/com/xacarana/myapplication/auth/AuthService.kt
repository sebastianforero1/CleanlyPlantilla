package com.xacarana.myapplication.auth

import android.content.Context
import android.content.SharedPreferences


/**
 * Servicio de autenticación local. Permite registrar, iniciar y cerrar sesión de usuarios usando SharedPreferences.
 * No utiliza Firebase, solo almacenamiento local.
 */
class AuthService(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)


    // Indica si el usuario está autenticado actualmente
    val isLoggedIn: Boolean
        get() = prefs.getBoolean("logged_in", false)


    // Devuelve el email del usuario autenticado
    fun currentEmail(): String? = prefs.getString("email", null)
    // Devuelve el nombre del usuario autenticado
    fun currentName(): String? = prefs.getString("name", null)

    /**
     * Inicia sesión verificando email y contraseña.
     * Retorna true si las credenciales son correctas.
     */
    fun login(email: String, password: String): Boolean {
        val savedEmail = prefs.getString("email", null)
        val savedPass = prefs.getString("password", null)
        val ok = (email.equals(savedEmail, ignoreCase = true) && password == savedPass)
        if (ok) prefs.edit().putBoolean("logged_in", true).apply()
        return ok
    }

    /**
     * Registra un nuevo usuario si el correo es válido (@gmail.com).
     * Guarda los datos y autentica al usuario.
     */
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

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logout() {
        prefs.edit().putBoolean("logged_in", false).apply()
    }
}
