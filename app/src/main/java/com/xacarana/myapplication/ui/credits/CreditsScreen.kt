package com.xacarana.myapplication.ui.credits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.ui.components.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditsScreen(nav: NavController) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Créditos",
                onNavigationClick = { /* No se usa cuando showBackButton es true */ },
                showBackButton = true,
                onBackClick = { nav.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier.padding(20.dp), 
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Aplicación realizada por:", 
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text("Sebastián Forero")
                    Text("Sebastián Villa")
                    Text("Julián Guisao")
                }
            }
        }
    }
}
