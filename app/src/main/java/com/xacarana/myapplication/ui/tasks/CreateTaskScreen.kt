@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.xacarana.myapplication.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.model.Repeat
import com.xacarana.myapplication.model.Task
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Composable
fun CreateTaskScreen(nav: NavController, repo: TaskRepository) {

    // Estados simples como String (evita .text)
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Selector de categoría desde catálogo
    val catalog = remember { repo.getCategories() }
    var category by remember { mutableStateOf(if (catalog.isNotEmpty()) catalog.first() else "") }
    var categoryExpanded by remember { mutableStateOf(false) }

    // Fecha / hora como texto amigable
    var dateStr by remember { mutableStateOf(LocalDate.now().toString()) } // YYYY-MM-DD
    var timeStr by remember { mutableStateOf("") } // HH:mm opcional

    // Repetición
    var repeat by remember { mutableStateOf(Repeat.NONE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Crear categorías / tareas", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        // Categoría - Exposed dropdown
        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = !categoryExpanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoría") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                singleLine = true
            )
            ExposedDropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false }
            ) {
                catalog.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            category = cat
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = dateStr,
            onValueChange = { dateStr = it },
            label = { Text("Fecha (YYYY-MM-DD)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = timeStr,
            onValueChange = { timeStr = it },
            label = { Text("Hora (HH:mm) opcional") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Chips de repetición
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = repeat == Repeat.NONE,
                onClick = { repeat = Repeat.NONE },
                label = { Text("Una vez") }
            )
            FilterChip(
                selected = repeat == Repeat.DAILY,
                onClick = { repeat = Repeat.DAILY },
                label = { Text("Diaria") }
            )
            FilterChip(
                selected = repeat == Repeat.WEEKLY,
                onClick = { repeat = Repeat.WEEKLY },
                label = { Text("Semanal") }
            )
            FilterChip(
                selected = repeat == Repeat.MONTHLY,
                onClick = { repeat = Repeat.MONTHLY },
                label = { Text("Mensual") }
            )
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                // Validaciones mínimas
                val date = runCatching { LocalDate.parse(dateStr) }.getOrNull() ?: LocalDate.now()
                val time = timeStr.trim().let { if (it.isBlank()) null else runCatching { LocalTime.parse(it) }.getOrNull() }

                val task = Task(
                    id = UUID.randomUUID().toString(),
                    title = title.trim(),
                    description = description.ifBlank { null },
                    category = category.ifBlank { "Sin categoría" },
                    date = date,
                    time = time,
                    repeat = repeat,
                    done = false,
                    createdAt = System.currentTimeMillis(),
                    completedAt = null
                )

                repo.save(task) // guarda + agenda recordatorio 1h antes si tiene hora
                nav.popBackStack() // volver al dashboard
            },
            enabled = title.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
