package com.xacarana.myapplication.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.model.Repeat
import com.xacarana.myapplication.model.Task
import com.xacarana.myapplication.navigation.Screen
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun CreateTaskScreen(nav: NavController, repo: TaskRepository) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var time by remember { mutableStateOf("") }
    var repeat by remember { mutableStateOf(Repeat.NONE) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Crear categorías / tareas")
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(title, { title = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(desc, { desc = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(category, { category = it }, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(date, { date = it }, label = { Text("Fecha (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(time, { time = it }, label = { Text("Hora (HH:mm) opcional") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = { repeat = Repeat.NONE }) { Text("Una vez") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { repeat = Repeat.DAILY }) { Text("Diaria") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { repeat = Repeat.WEEKLY }) { Text("Semanal") }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            val localTime = time.takeIf { it.isNotBlank() }?.let { LocalTime.parse(it) }
            val task = Task(
                title = title.trim(),
                description = desc.trim().ifBlank { null },
                category = category.trim(),
                date = LocalDate.parse(date),
                time = localTime,
                repeat = repeat
            )
            repo.save(task) // => programará recordatorio 1h antes si tiene hora
            nav.navigate(Screen.Dashboard.route) { popUpTo(Screen.CreateTask.route) { inclusive = true } }
        }, modifier = Modifier.fillMaxWidth()) { Text("Guardar") }
    }
}
