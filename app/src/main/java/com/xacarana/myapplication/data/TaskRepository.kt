package com.xacarana.myapplication.data

import android.content.Context
import com.xacarana.myapplication.model.ActivityRecord
import com.xacarana.myapplication.model.Task
import java.time.LocalDate

/**
 * Interfaz para la gestión de tareas y actividades.
 * Define los métodos para CRUD de tareas, categorías y persistencia de datos.
 */
interface TaskRepository {
    /**
     * Obtiene la lista de tareas, opcionalmente filtradas por fecha.
     */
    fun getTasks(date: LocalDate? = null): List<Task>
    /**
     * Devuelve una tarea específica por su ID.
     */
    fun getTask(id: String): Task?
    /**
     * Guarda o actualiza una tarea en el repositorio.
     */
    fun save(task: Task)
    /**
     * Cambia el estado de completado de una tarea.
     */
    fun toggleDone(id: String): Task?
    /**
     * Elimina una tarea por su ID.
     */
    fun delete(id: String)

    /**
     * 🔁 Compatibilidad hacia atrás:
     *   - categories(): se mantiene por si lo usabas antes.
     *   - getCategories(): API nueva y preferida.
     */
    fun categories(): List<String> = getCategories()

    /** Categorías independientes (persistidas). */
    /**
     * Devuelve la lista de categorías de tareas.
     */
    fun getCategories(): List<String>
    /**
     * Agrega una nueva categoría de tareas.
     */
    fun addCategory(name: String)
    /**
     * Elimina una categoría de tareas por nombre.
     */
    fun removeCategory(name: String)

    /**
     * Devuelve el historial de actividades realizadas.
     */
    fun records(): List<ActivityRecord>

    // Helpers
    /**
     * Persiste los datos en almacenamiento local.
     */
    fun persist()
    /**
     * Carga los datos desde almacenamiento local.
     */
    fun load(context: Context)
}
