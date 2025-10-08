package com.xacarana.myapplication.model

data class User(
    val id: String,
    val name: String
)

data class FeedItem(
    val id: String,
    val title: String,
    val description: String,
    val author: User? = null
)
