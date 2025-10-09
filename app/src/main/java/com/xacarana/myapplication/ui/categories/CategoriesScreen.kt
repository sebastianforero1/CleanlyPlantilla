package com.xacarana.myapplication.ui.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.xacarana.myapplication.data.TaskRepository

@Composable
fun CategoriesScreen(repo: TaskRepository) {
    var newCat by remember { mutableStateOf(TextFieldValue("")) }
    // volver a leer cada vez que se cambie algo (simple sin Flow)
    var refresh by remember { mutableStateOf(0) }
    val categories by remember(refresh) { mutableStateOf(repo.getCategories()) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Crear categorías", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = newCat,
                onValueChange = { newCat = it },
                label = { Text("Añadir") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    val n = newCat.text.trim()
                    if (n.isNotEmpty()) {
                        repo.addCategory(n)
                        newCat = TextFieldValue("")
                        refresh++
                    }
                },
                modifier = Modifier.height(56.dp)
            ) { Text("Guardar") }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { cat ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(cat, style = MaterialTheme.typography.titleMedium)
                        IconButton(onClick = {
                            repo.removeCategory(cat)
                            refresh++
                        }) {
                            Icon(Icons.Outlined.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
