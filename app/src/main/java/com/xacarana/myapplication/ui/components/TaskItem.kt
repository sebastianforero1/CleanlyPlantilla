package com.xacarana.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.xacarana.myapplication.R
import com.xacarana.myapplication.model.Task
import java.time.format.DateTimeFormatter

@Composable
fun TaskItem(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onOpen: () -> Unit
) {
    val style = if (task.done) MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough)
    else MaterialTheme.typography.bodyLarge

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Checkbox(checked = task.done, onCheckedChange = { onToggle() })
            Text(
                text = buildString {
                    append(task.title)
                    task.time?.let { append("  ·  ${it.format(DateTimeFormatter.ofPattern("HH:mm"))}") }
                },
                style = style,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        IconButton(onClick = onOpen) {
            Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Detalle")
        }
        IconButton(onClick = onDelete) {
            Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Eliminar")
        }
    }
}
