package com.xacarana.myapplication.ui.dashboard

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.model.Task
import com.xacarana.myapplication.navigation.Screen
import com.xacarana.myapplication.ui.components.CalendarRow
import com.xacarana.myapplication.ui.components.TaskItem
import com.xacarana.myapplication.util.DateUtils
import com.xacarana.myapplication.ui.components.DayStats
import java.time.LocalDate

/**
 * Dashboard agrupado por categoría:
 * - Mantiene API actual del repo: getTasks(date), toggleDone(id), delete(id)
 * - Encabezados de sección por categoría (orden: catálogo del repo y luego "Sin categoría")
 * - Botón Compartir (lista del día)
 * - Botón Añadir tarea
 * - Recompose al marcar/eliminar (refresh++)
 */
@Composable
fun DashboardScreen(nav: NavController, repo: TaskRepository) {
    var selectedDay by remember { mutableStateOf(LocalDate.now()) }
    var refresh by remember { mutableStateOf(0) } // fuerza recomposición cuando cambian tareas
    val context = LocalContext.current

    // Tareas del día (según tu API actual)
    val tasksForDay by remember(selectedDay, refresh) {
        mutableStateOf(repo.getTasks(selectedDay))
    }

    // Orden de categorías: primero las definidas en catálogo, luego cualquier otra que traigan las tareas, y por último "Sin categoría"
    val catalog = remember(refresh) { repo.getCategories() }
    val taskCats = tasksForDay.map { it.category.ifBlank { "Sin categoría" } }.distinct()
    val orderedCategories = buildList {
        addAll(catalog.filter { it in taskCats })
        addAll(taskCats.filterNot { it in catalog || it == "Sin categoría" })
        if (taskCats.any { it == "Sin categoría" }) add("Sin categoría")
    }

    Column(Modifier.fillMaxSize()) {

        // Encabezado superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Hoy", style = MaterialTheme.typography.headlineMedium)
            IconButton(
                onClick = {
                    val text = buildShareText(tasksForDay, selectedDay)
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartir checklist"))
                }
            ) { Icon(Icons.Outlined.Share, contentDescription = "Compartir") }
        }

        // Semana compacta
        CalendarRow(
            days = com.xacarana.myapplication.util.DateUtils.weekRange(selectedDay),
            selected = selectedDay,
            onSelect = { selectedDay = it },
            // Proveedor de estadísticas por día (usa tu repo)
            statsProvider = { d ->
                val list = repo.getTasks(d)
                DayStats(total = list.size, done = list.count { it.done })
            }
        )

        Spacer(Modifier.height(8.dp))

        // Lista agrupada por categoría
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            if (tasksForDay.isEmpty()) {
                item {
                    EmptyState(modifier = Modifier.fillParentMaxSize())
                }
            } else {
                orderedCategories.forEach { cat ->
                    val itemsInCat = tasksForDay.filter { (it.category.ifBlank { "Sin categoría" }) == cat }
                    if (itemsInCat.isNotEmpty()) {
                        // Header de categoría
                        item(key = "header_$cat") {
                            CategoryHeader(cat = cat)
                        }
                        // Items de la categoría
                        items(itemsInCat, key = { it.id }) { task ->
                            TaskItem(
                                task = task,
                                onToggle = {
                                    repo.toggleDone(task.id)
                                    refresh++
                                },
                                onDelete = {
                                    repo.delete(task.id)
                                    refresh++
                                },
                                onOpen = { nav.navigate(Screen.TaskDetail.route(task.id)) }
                            )
                        }
                        // Separación entre grupos
                        item(key = "spacer_$cat") { Spacer(Modifier.height(4.dp)) }
                    }
                }
            }
        }

        // Botón añadir
        Button(
            onClick = { nav.navigate(Screen.CreateTask.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) { Text("Añadir") }
    }
}

@Composable
private fun CategoryHeader(cat: String) {
    // Estética limpia tipo “sección” (alineado al Figma)
    Text(
        text = cat.uppercase(),
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp)
    )
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.Inbox,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "No hay tareas para este día",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun buildShareText(list: List<Task>, date: LocalDate): String = buildString {
    append("Checklist de limpieza - $date\n")
    if (list.isEmpty()) {
        append("• (sin tareas)\n")
        return@buildString
    }
    val grouped = list.groupBy { it.category.ifBlank { "Sin categoría" } }
    grouped.toSortedMap(String.CASE_INSENSITIVE_ORDER).forEach { (cat, tasks) ->
        append("\n[$cat]\n")
        tasks.forEach { t ->
            val done = if (t.done) "✔" else "❒"
            val time = t.time?.let { " ($it)" } ?: ""
            append("$done ${t.title}$time\n")
        }
    }
}
