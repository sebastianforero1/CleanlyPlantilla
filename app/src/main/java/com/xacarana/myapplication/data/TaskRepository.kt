package com.xacarana.myapplication.data

import android.content.Context
import com.xacarana.myapplication.model.ActivityRecord
import com.xacarana.myapplication.model.Task
import java.time.LocalDate

interface TaskRepository {
    fun getTasks(date: LocalDate? = null): List<Task>
    fun getTask(id: String): Task?
    fun save(task: Task)
    fun toggleDone(id: String): Task?
    fun delete(id: String)

    /**
     * 🔁 Compatibilidad hacia atrás:
     *   - categories(): se mantiene por si lo usabas antes.
     *   - getCategories(): API nueva y preferida.
     */
    fun categories(): List<String> = getCategories()

    /** Categorías independientes (persistidas). */
    fun getCategories(): List<String>
    fun addCategory(name: String)
    fun removeCategory(name: String)

    fun records(): List<ActivityRecord>

    // Helpers
    fun persist()
    fun load(context: Context)
}
