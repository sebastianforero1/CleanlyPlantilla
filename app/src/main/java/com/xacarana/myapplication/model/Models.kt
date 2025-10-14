package com.xacarana.myapplication.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

// ===== Modelos propios de Cleanly =====

/**
 * Modelo de categoría simple para agrupar tareas (ejemplo: Cocina, Baño, Sala, etc.)
 */
data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String
)

/**
 * Modelo de tarea de limpieza. Incluye información sobre título, descripción, categoría, fecha, hora, repetición y estado.
 */
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

/**
 * Modelo de registro histórico para auditar tareas completadas.
 */
data class ActivityRecord(
    val taskId: String,
    val title: String,
    val completedAt: Long = System.currentTimeMillis()
)
