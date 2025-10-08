package com.xacarana.myapplication.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.auth.AuthService
import com.xacarana.myapplication.navigation.Screen

@Composable
fun ProfileScreen(nav: NavController, auth: AuthService) {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(text = "Buenos días ${auth.userEmail}")
        Spacer(Modifier.height(12.dp))
        Button(onClick = { nav.navigate(Screen.Credits.route) }, modifier = Modifier.fillMaxWidth()) {
            Text("Créditos")
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            auth.logout()
            nav.navigate(Screen.Welcome.route) { popUpTo(Screen.Profile.route) { inclusive = true } }
        }, modifier = Modifier.fillMaxWidth()) { Text("Cerrar Sesión") }
    }
}
