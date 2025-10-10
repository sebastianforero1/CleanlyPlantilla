package com.xacarana.myapplication.ui.credits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CreditsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Créditos", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold))
        Spacer(Modifier.height(16.dp))
        ElevatedCard(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Aplicación realizada por:", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                Text("Sebastián Forero")
                Text("Sebastián Villa")
                Text("Julián Guisao")
            }
        }
    }
}
