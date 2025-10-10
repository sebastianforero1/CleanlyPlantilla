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
fun SignUpScreen(nav: NavController, auth: AuthService) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("¿Cómo te llamas?") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo (Gmail)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Crea una contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85f)
            )

            // Mensaje de validación local
            error?.let {
                Text(
                    it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    val emailStr = email.trim()
                    val nameStr = name.trim()
                    val passStr = pass

                    // Validaciones
                    if (nameStr.isBlank()) { error = "El nombre es obligatorio"; return@Button }
                    if (!emailStr.matches(Regex("^[A-Za-z0-9._%+-]+@gmail\\.com$"))) {
                        error = "Ingresa un correo válido (terminado en @gmail.com)"
                        return@Button
                    }
                    if (passStr.length < 6) { error = "La contraseña debe tener al menos 6 caracteres"; return@Button }

                    error = null
                    scope.launch {
                        runCatching { auth.signUp(nameStr, emailStr, passStr) }
                            .onSuccess {
                                nav.navigate(Screen.Dashboard.route) {
                                    popUpTo(Screen.Welcome.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                            .onFailure { ex ->
                                snack.showSnackbar(ex.message ?: "No se pudo registrar")
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text("Crear cuenta")
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
