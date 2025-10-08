package com.xacarana.myapplication.ui.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavController
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.navigation.Screen

@Composable
fun TaskDetailScreen(nav: NavController, repo: TaskRepository, taskId: String) {
    val task = repo.getTask(taskId) ?: return
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Detalle")
        Spacer(Modifier.height(12.dp))
        Text("Título: ${task.title}")
        Text("Categoría: ${task.category}")
        Text("Fecha: ${task.date}")
        Text("Hora: ${task.time ?: "—"}")
        Text("Repetición: ${task.repeat}")
        Text("Estado: ${if (task.done) "Hecha" else "Pendiente"}")
        Spacer(Modifier.height(12.dp))
        Button(onClick = { repo.toggleDone(task.id) }) { Text(if (task.done) "Desmarcar" else "Marcar como hecha") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            repo.delete(task.id)
            nav.navigate(Screen.Dashboard.route) { popUpTo(Screen.Dashboard.route) { inclusive = true } }
        }) { Text("Eliminar") }
    }
}
