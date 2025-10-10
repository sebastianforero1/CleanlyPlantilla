package com.xacarana.myapplication.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.auth.AuthService
import com.xacarana.myapplication.navigation.Screen
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun LoginScreen(nav: NavController, auth: AuthService) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snack = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        // Contenido centrado
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                "Iniciar sesión",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            Button(
                onClick = {
                    scope.launch {
                        val ok: Boolean = auth.login(email.trim(), pass)
                        if (ok) {
                            nav.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        } else {
                            snack.showSnackbar("Error de autenticación")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Iniciar sesión")
            }
        }

        // Snackbars abajo
        SnackbarHost(
            hostState = snack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}
