package com.xacarana.myapplication.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("¿Cómo te llamas?") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("¿Cuál es tu correo?") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Crea una contraseña") },
            visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            scope.launch {
                val res = auth.signup(email.trim(), pass, name.trim())
                if (res.isSuccess) {
                    nav.navigate(Screen.Dashboard.route) { popUpTo(Screen.SignUp.route) { inclusive = true } }
                }
            }
        }, modifier = Modifier.fillMaxWidth()) { Text("Siguiente") }
    }
}
