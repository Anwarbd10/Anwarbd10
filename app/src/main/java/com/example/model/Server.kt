package com.example.model

data class Server(
    val id: String,
    val name: String,
    val country: String,
    val flag: String,
    val latencyMs: Int,
    val locationsCount: Int,
    val isPremium: Boolean = false,
    val isBangladesh: Boolean = false,
    val description: String = ""
)
