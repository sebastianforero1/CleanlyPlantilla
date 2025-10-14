package com.xacarana.myapplication.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xacarana.myapplication.data.TaskRepository

@Composable
fun CategoriesScreen(repo: TaskRepository) {
    var newCategory by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf(repo.categories().toMutableList()) }

    fun addCategory(category: String) {
        if (category.isNotEmpty() && !categories.contains(category)) {
            val newList = categories.toMutableList().apply { add(category) }
            categories = newList
            repo.persist()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "Crear categorías",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Campo + botón alineados
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newCategory,
                    onValueChange = { newCategory = it },
                    label = { Text("Añadir") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp) // igual altura al botón
                )

                Spacer(Modifier.width(12.dp))

                Button(
                    onClick = {
                        val trimmed = newCategory.trim()
                        addCategory(trimmed)
                        newCategory = ""
                    },
                    modifier = Modifier
                        .height(56.dp) //  misma altura que el TextField
                        .width(110.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Guardar", textAlign = TextAlign.Center)
                }
            }

            // Lista de categorías
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        )
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                cat,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            IconButton(onClick = {
                                val newList = categories.toMutableList().apply { remove(cat) }
                                categories = newList
                                repo.persist()
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
