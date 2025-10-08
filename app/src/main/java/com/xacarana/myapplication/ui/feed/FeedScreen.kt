package com.xacarana.myapplication.ui.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.xacarana.myapplication.model.FeedItem

@Composable
fun FeedScreen(items: List<FeedItem>) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(items) { it ->
            Text(it.title, style = MaterialTheme.typography.titleMedium)
            Text(it.description, style = MaterialTheme.typography.bodyMedium)
            Text(it.author?.name ?: "", style = MaterialTheme.typography.labelSmall)
            Text("") // separador simple
        }
    }
}
