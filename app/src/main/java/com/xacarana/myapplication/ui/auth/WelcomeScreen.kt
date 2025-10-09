package com.xacarana.myapplication.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.navigation.Screen

@Composable
fun WelcomeScreen(nav: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        // Logo/monograma centrado arriba (círculo con "C")
        Box(
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
            contentAlignment = Alignment.Center
        ) {
            Text("C", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Bienvenido a Cleanly",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { nav.navigate(Screen.SignUp.route) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Registrarse", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = { nav.navigate(Screen.Login.route) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Iniciar sesión", style = MaterialTheme.typography.bodyLarge)
            }
        }

        // Frase inferior
        Text(
            text = "“Bienvenido a Cleanly, ordenamos tu tranquilidad”",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
