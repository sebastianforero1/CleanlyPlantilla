package com.xacarana.myapplication.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.xacarana.myapplication.auth.AuthService
import com.xacarana.myapplication.navigation.Screen
import kotlinx.coroutines.delay

/**
 * Splash muy defensivo:
 * - No hace ningún trabajo pesado en composición.
 * - Decide destino tras un pequeño delay.
 * - Navega con popUpTo(inclusive) para no dejar el Splash en el back stack.
 */
@Composable
fun SplashScreen(nav: NavController, auth: AuthService) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Muestra la "C" / nombre del proyecto
        Text(text = "Cleanly", style = MaterialTheme.typography.headlineLarge)
    }

    LaunchedEffect(Unit) {
        // Pequeña pausa para renderizar Splash sin bloquear
        delay(700)

        // Navegación segura (evita rutas inexistentes)
        val target = if (auth.isLoggedIn) Screen.Dashboard.route else Screen.Welcome.route
        // Protección contra errores de navegación: si la ruta no existe en el NavHost, no intentamos navegar.
        runCatching {
            nav.navigate(target) {
                popUpTo(Screen.Splash.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}
