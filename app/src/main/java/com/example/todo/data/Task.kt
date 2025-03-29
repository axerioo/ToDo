package com.example.todo.data

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
    val taskId: Long = System.currentTimeMillis()
)
