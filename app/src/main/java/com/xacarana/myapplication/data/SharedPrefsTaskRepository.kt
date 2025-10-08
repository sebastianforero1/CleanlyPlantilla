package com.xacarana.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import com.xacarana.myapplication.model.ActivityRecord
import com.xacarana.myapplication.model.Repeat
import com.xacarana.myapplication.model.Task
import com.xacarana.myapplication.notifications.ReminderScheduler
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime

/**
 * Persistencia local usando SharedPreferences en JSON.
 * No añade dependencias. org.json viene en el SDK.
 */
class SharedPrefsTaskRepository(
    private val appContext: Context
) : TaskRepository {

    private val prefs: SharedPreferences by lazy {
        appContext.getSharedPreferences("cleanly_prefs", Context.MODE_PRIVATE)
    }

    private val tasks = mutableListOf<Task>()
    private val activityLog = mutableListOf<ActivityRecord>()

    override fun load(context: Context) {
        tasks.clear()
        activityLog.clear()

        val json = prefs.getString("tasks_json", "[]") ?: "[]"
        val arr = JSONArray(json)
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            tasks += o.toTask()
        }

        val recJson = prefs.getString("records_json", "[]") ?: "[]"
        val recArr = JSONArray(recJson)
        for (i in 0 until recArr.length()) {
            val o = recArr.getJSONObject(i)
            activityLog += ActivityRecord(
                taskId = o.getString("taskId"),
                title = o.getString("title"),
                completedAt = o.getLong("completedAt")
            )
        }
    }

    override fun persist() {
        val arr = JSONArray()
        tasks.forEach { arr.put(it.toJson()) }
        prefs.edit().putString("tasks_json", arr.toString()).apply()

        val recArr = JSONArray()
        activityLog.forEach {
            recArr.put(JSONObject().apply {
                put("taskId", it.taskId)
                put("title", it.title)
                put("completedAt", it.completedAt)
            })
        }
        prefs.edit().putString("records_json", recArr.toString()).apply()
    }

    override fun getTasks(date: LocalDate?): List<Task> =
        if (date == null) tasks.toList()
        else tasks.filter { it.date == date }.sortedBy { it.time ?: LocalTime.MIN }

    override fun getTask(id: String): Task? = tasks.firstOrNull { it.id == id }

    override fun save(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index >= 0) tasks[index] = task else tasks += task
        persist()

        // Si tiene hora, programar recordatorio 1h antes
        if (task.time != null) {
            ReminderScheduler.scheduleOneHourBefore(appContext, task)
        }
    }

    override fun toggleDone(id: String): Task? {
        val idx = tasks.indexOfFirst { it.id == id }
        if (idx < 0) return null
        val cur = tasks[idx]
        val toggled = cur.copy(done = !cur.done, completedAt = if (!cur.done) System.currentTimeMillis() else null)
        tasks[idx] = toggled
        persist()

        if (toggled.done) {
            // guardar registro
            activityLog += ActivityRecord(taskId = toggled.id, title = toggled.title)
            persist()

            // Si la tarea tiene repetición, crear siguiente ocurrencia
            if (toggled.repeat != Repeat.NONE) {
                val nextDate = when (toggled.repeat) {
                    Repeat.DAILY -> toggled.date.plusDays(1)
                    Repeat.WEEKLY -> toggled.date.plusWeeks(1)
                    Repeat.MONTHLY -> toggled.date.plusMonths(1)
                    Repeat.NONE -> null
                }
                nextDate?.let {
                    val next = toggled.copy(
                        id = java.util.UUID.randomUUID().toString(),
                        date = it,
                        done = false,
                        completedAt = null,
                        createdAt = System.currentTimeMillis()
                    )
                    save(next)
                }
            }
        }
        return toggled
    }

    override fun delete(id: String) {
        tasks.removeAll { it.id == id }
        persist()
    }

    override fun categories(): List<String> =
        tasks.map { it.category }.toSet().sorted()

    override fun records(): List<ActivityRecord> = activityLog.toList()

    // ==== helpers json <-> model ====
    private fun Task.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("title", title)
        put("description", description)
        put("category", category)
        put("date", date.toString())
        put("time", time?.toString())
        put("repeat", repeat.name)
        put("done", done)
        put("createdAt", createdAt)
        put("completedAt", completedAt)
    }

    private fun JSONObject.toTask(): Task = Task(
        id = getString("id"),
        title = getString("title"),
        description = optString("description", null),
        category = getString("category"),
        date = LocalDate.parse(getString("date")),
        time = optString("time", null)?.let { LocalTime.parse(it) },
        repeat = Repeat.valueOf(getString("repeat")),
        done = getBoolean("done"),
        createdAt = getLong("createdAt"),
        completedAt = if (has("completedAt") && !isNull("completedAt")) getLong("completedAt") else null
    )
}
