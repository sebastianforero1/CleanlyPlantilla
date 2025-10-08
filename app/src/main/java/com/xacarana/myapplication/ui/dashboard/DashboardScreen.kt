package com.xacarana.myapplication.ui.dashboard

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.R
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.model.Task
import com.xacarana.myapplication.navigation.Screen
import com.xacarana.myapplication.ui.components.CalendarRow
import com.xacarana.myapplication.ui.components.TaskItem
import com.xacarana.myapplication.util.DateUtils
import java.time.LocalDate

@Composable
fun DashboardScreen(nav: NavController, repo: TaskRepository) {
    var selectedDay by remember { mutableStateOf(LocalDate.now()) }
    val tasks by remember(selectedDay) { mutableStateOf(repo.getTasks(selectedDay)) }
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        // Encabezado "Hoy" + compartir
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Hoy", style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = {
                // Compartir lista del día
                val text = buildShareText(repo.getTasks(selectedDay), selectedDay)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
            }) {
                Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Compartir")
            }
        }

        CalendarRow(
            days = DateUtils.weekRange(selectedDay),
            selected = selectedDay,
            onSelect = {
                selectedDay = it
            }
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn(Modifier.weight(1f)) {
            items(repo.getTasks(selectedDay)) { task ->
                TaskItem(
                    task = task,
                    onToggle = {
                        repo.toggleDone(task.id)
                    },
                    onDelete = { repo.delete(task.id) },
                    onOpen = { nav.navigate(Screen.TaskDetail.route(task.id)) }
                )
            }
        }

        Button(
            onClick = { nav.navigate(Screen.CreateTask.route) },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) { Text("Añadir") }
    }
}

private fun buildShareText(list: List<Task>, date: LocalDate): String = buildString {
    append("Checklist de limpieza - ${date}\n")
    list.forEach {
        val done = if (it.done) "✔" else "❒"
        append("$done ${it.title}${it.time?.let { t -> " (${t})" } ?: ""}\n")
    }
}
