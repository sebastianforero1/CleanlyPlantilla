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

@Composable
fun SignUpScreen(nav: NavController, auth: AuthService) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val snack = remember { SnackbarHostState() }

    Box(Modifier.fillMaxSize().padding(16.dp)) {
        Column(Modifier.fillMaxWidth()) {
            Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("¿Cómo te llamas?") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo (Gmail)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Crea una contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    val emailStr = email.trim()
                    val nameStr = name.trim()
                    val passStr = pass

                    // Validaciones
                    if (nameStr.isBlank()) { error = "El nombre es obligatorio"; return@Button }
                    if (!emailStr.matches(Regex("^[A-Za-z0-9._%+-]+@gmail\\.com$"))) {
                        error = "Ingresa un correo Gmail válido (terminado en @gmail.com)"
                        return@Button
                    }
                    if (passStr.length < 6) { error = "La contraseña debe tener al menos 6 caracteres"; return@Button }

                    error = null
                    scope.launch {
                        // signUp de tu AuthService no devuelve Result -> si no lanza error, navegamos
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
                modifier = Modifier.fillMaxWidth()
            ) { Text("Crear cuenta") }
        }

        SnackbarHost(hostState = snack, modifier = Modifier.align(Alignment.BottomCenter))
    }
}
