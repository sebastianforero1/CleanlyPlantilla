package com.xacarana.myapplication.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.xacarana.myapplication.data.TaskRepository

@Composable
fun CategoriesScreen(repo: TaskRepository) {
    val cats = repo.categories()
    Column(Modifier.fillMaxSize()) {
        Text("Crear categorías")
        LazyColumn {
            items(cats) { c -> Text("- $c") }
        }
    }
}
