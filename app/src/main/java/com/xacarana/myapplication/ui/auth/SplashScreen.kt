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
import com.xacarana.myapplication.navigation.Screen
import com.xacarana.myapplication.auth.AuthService
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(nav: NavController, auth: AuthService) {
    // Pantalla splash simple (evita importar material “antiguo” para no chocar con material3)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Cleanly", style = MaterialTheme.typography.headlineLarge)
    }
    LaunchedEffect(Unit) {
        delay(700)
        if (auth.isLoggedIn) {
            nav.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            nav.navigate(Screen.Welcome.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }
}
