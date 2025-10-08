package com.xacarana.myapplication.ui.credits

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreditsScreen() {
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text("Aplicación realizada por:")
        Spacer(Modifier.height(12.dp))
        Text("• Sebastián Forero")
        Text("• Sebastián Villa")
        Text("• Julián Guisao")
    }
}
