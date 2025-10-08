package com.xacarana.myapplication.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Pantalla de configuración simple.
 * No usa fuentes externas (getSettings) para que compile sin dependencias.
 * Si quieres persistir los switches, podemos moverlos a SharedPreferences/DataStore luego.
 */
@Composable
fun SettingsScreen() {
    var recordatorios by remember { mutableStateOf(true) }
    var repetirAuto by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Configuración", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        SettingSwitchRow(
            title = "Recordatorios de tareas",
            subtitle = "Notificar 1 hora antes de la actividad",
            checked = recordatorios,
            onCheckedChange = { recordatorios = it }
        )
        Divider()
        SettingSwitchRow(
            title = "Repetición automática",
            subtitle = "Crear la siguiente ocurrencia al marcar como hecha",
            checked = repetirAuto,
            onCheckedChange = { repetirAuto = it }
        )
    }
}

@Composable
private fun SettingSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(2.dp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
