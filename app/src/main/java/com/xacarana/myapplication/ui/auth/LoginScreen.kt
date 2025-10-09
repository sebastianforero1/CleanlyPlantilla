package com.xacarana.myapplication.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.auth.AuthService
import com.xacarana.myapplication.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(nav: NavController, auth: AuthService) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snack = remember { SnackbarHostState() }

    Box(Modifier.fillMaxSize().padding(24.dp)) {
        Column {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }
        }

        SnackbarHost(hostState = snack, modifier = Modifier.align(Alignment.BottomCenter))
    }
}
