package com.xacarana.myapplication.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

// ===== Modelos propios de Cleanly =====

// Categoría simple para agrupar tareas (Cocina, Baño, Sala, etc.)
data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String
)

// Tarea de limpieza
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val category: String = "General",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime? = null,       // hora objetivo opcional
    val repeat: Repeat = Repeat.NONE,  // repetición automática
    val done: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)

// Registro histórico (audit trail) para “tarea completada”
data class ActivityRecord(
    val taskId: String,
    val title: String,
    val completedAt: Long = System.currentTimeMillis()
)
