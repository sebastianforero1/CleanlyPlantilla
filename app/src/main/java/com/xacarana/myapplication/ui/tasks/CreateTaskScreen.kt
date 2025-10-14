package com.xacarana.myapplication.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.model.Repeat
import com.xacarana.myapplication.ui.components.AppToolbar
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(nav: NavController, repo: TaskRepository) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var time by remember { mutableStateOf("") }
    var repeat by remember { mutableStateOf(Repeat.NONE) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Nueva tarea",
                onNavigationClick = { /* No se usa cuando showBackButton es true */ },
                showBackButton = true,
                onBackClick = { nav.popBackStack() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoría") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Fecha (YYYY-MM-DD)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Hora (HH:mm) opcional") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Botones de repetición
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(
                        Repeat.NONE to "Una vez",
                        Repeat.DAILY to "Diaria",
                        Repeat.WEEKLY to "Semanal",
                        Repeat.MONTHLY to "Mensual"
                    ).forEach { (mode, label) ->
                        FilterChip(
                            selected = repeat == mode,
                            onClick = { repeat = mode },
                            label = { Text(label) }
                        )
                    }
                }

                error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (title.isBlank()) {
                            error = "El título es obligatorio"
                            return@Button
                        }

                        val task = com.xacarana.myapplication.model.Task(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            description = description.ifBlank { null },
                            category = category.ifBlank { "General" },
                            date = LocalDate.parse(date),
                            time = time.ifBlank { null }?.let { LocalTime.parse(it) },
                            repeat = repeat
                        )

                        repo.save(task)
                        nav.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = title.isNotBlank()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
