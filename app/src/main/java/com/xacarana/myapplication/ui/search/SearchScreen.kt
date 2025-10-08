package com.xacarana.myapplication.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val (query, setQuery) = remember { mutableStateOf("") }
    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = setQuery,
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = if (query.isBlank()) "Type something to search..." else "No results for \"$query\" (mock)",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}






