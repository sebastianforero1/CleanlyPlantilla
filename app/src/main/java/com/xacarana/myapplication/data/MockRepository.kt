package com.xacarana.myapplication.data

import com.xacarana.myapplication.model.FeedItem
import com.xacarana.myapplication.model.User

object MockRepository {

    fun feed(): List<FeedItem> = listOf(
        FeedItem(
            id = "1",
            title = "Lavar platos",
            description = "Cocina · 15 min",
            author = User("u1", "Sebastián")
        ),
        FeedItem(
            id = "2",
            title = "Limpiar nevera",
            description = "Cocina · 20 min",
            author = User("u2", "Julián")
        )
    )
}
