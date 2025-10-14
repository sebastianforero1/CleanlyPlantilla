package com.xacarana.myapplication.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.navigation.Screen
import com.xacarana.myapplication.ui.components.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(nav: NavController, repo: TaskRepository, taskId: String) {
    val task = repo.getTask(taskId) ?: run {
        nav.popBackStack()
        return
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = "Detalle de tarea",
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
                .padding(16.dp)
        ) {
            Text("Título: ${task.title}")
            Spacer(Modifier.height(8.dp))
            Text("Categoría: ${task.category}")
            Text("Fecha: ${task.date}")
            Text("Hora: ${task.time ?: "—"}")
            Text("Repetición: ${task.repeat}")
            Text("Estado: ${if (task.done) "Hecha" else "Pendiente"}")
            
            Spacer(Modifier.height(16.dp))
            
            Button(
                onClick = { 
                    repo.toggleDone(task.id)
                },
                modifier = Modifier.fillMaxWidth()
            ) { 
                Text(if (task.done) "Desmarcar" else "Marcar como hecha") 
            }
            
            Spacer(Modifier.height(8.dp))
            
            Button(
                onClick = {
                    repo.delete(task.id)
                    nav.navigate(Screen.Dashboard.route) { 
                        popUpTo(Screen.Dashboard.route) { inclusive = true } 
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { 
                Text("Eliminar") 
            }
        }
    }
}
