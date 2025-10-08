package com.xacarana.myapplication.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.navigation.Screen

@Composable
fun WelcomeScreen(nav: NavController) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text("Bienvenido a Cleanly", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        Button(onClick = { nav.navigate(Screen.SignUp.route) }, modifier = Modifier.fillMaxWidth()) {
            Text("Registrarse")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { nav.navigate(Screen.Login.route) }, modifier = Modifier.fillMaxWidth()) {
            Text("Iniciar sesión")
        }
    }
}
