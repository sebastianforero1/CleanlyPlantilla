package com.xacarana.myapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Details
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xacarana.myapplication.model.Task
import java.time.format.DateTimeFormatter

/**
 * Item de tarea.
 * - Sin estados locales para el checkbox (usa task.done directamente).
 * - Sin !! sobre campos opcionales.
 * - Iconos acordes: Edit y Delete.
 */
@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onOpen: () -> Unit = {}
) {
    val timeText = task.time?.format(DateTimeFormatter.ofPattern("HH:mm"))

    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { _ -> onToggle() }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onOpen() }
                    .padding(start = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (timeText != null) {
                        Text(
                            text = " · $timeText",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }
                }
                task.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Acciones
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Editar tarea"
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Eliminar tarea"
                )
            }
        }
        Divider(thickness = 0.5.dp)
    }
}
