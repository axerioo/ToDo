package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_tasks")
/**
 * Data class representing a task. Data classes are special classes in Kotlin that are used to
 * represent data, the benefits of using a data class are that it automatically generates
 * `equals()`, `hashCode()`, `toString()`, and `copy()` methods for you.
 *
 * @param name The name of the task.
 * @param description The description of the task.
 * @param isCompleted The completion status of the task.
 * @param isImportant The importance status of the task.
 * @param taskId The unique ID of the task (also a timestamp of creation).
 */
data class Task(
    var name: String,
    var description: String,
    var isCompleted: Boolean = false,
    var isImportant: Boolean = false,
    @PrimaryKey
    val taskId: Long = System.currentTimeMillis()
)

/**
 * Convert a list of tasks to a string.
 * @param tasks The list of tasks to convert.
 * @return The  string representing the list of tasks.
 */
fun convertToString(tasks: List<Task>): String {
    return buildString {
        append("Tasks:\n")
        tasks.forEach {
            append("${it.name}\n${it.description}\n")
            append("Completed: ${it.isCompleted}\n")
            append("Important: ${it.isImportant}\n")
            append("Task ID: ${it.taskId}\n")
            append("--------\n")
        }
    }
}
