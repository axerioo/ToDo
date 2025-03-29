package com.example.todo.data

import androidx.compose.runtime.mutableStateListOf

/**
 * A simple object to hold the list of tasks and allows for creating dummy content.
 * It will be replaced by a database later on.
 */
object DataSource {
    /** A list of tasks. */
    private val tasks = mutableStateListOf<Task>().apply { addAll(getDummyContent(3)) }
    /**
     * Get a list of tasks with dummy content.
     * @param numElements the number of tasks to generate
     * @return a list of tasks
     */
    fun getDummyContent(numElements: Int): List<Task> {
        val list = mutableListOf<Task>()
        for (i in 0 until numElements) {
            // Create a task with a unique ID using the current time in milliseconds and some
            // default values for the name and description
            list.add(
                Task(
                    name = "Task $i",
                    description = "Description $i",
                    taskId = System.currentTimeMillis() + i * 1000
                )
            )
        }
        return list
    }
    /**
     * Add a task to the list.
     * @param task the task to add
     */
    fun addTask(task: Task) {
        tasks.add(task)
    }
    /**
     * Get the list of tasks.
     * @return the list of tasks
     */
    fun getTasks(): List<Task> {
        return tasks
    }
    /**
     * Delete a task from the list.
     * @param task the task to delete
     */
    fun deleteTask(task: Task) {
        tasks.remove(task)
    }
    /**
     * Update a task in the list.
     * @param task the task to update
     */
    fun updateTask(task: Task) {
        // Find the index of the task with the same ID as the updated task
        val index = tasks.indexOfFirst { it.taskId == task.taskId }
        // If the task is found the index will be greater than -1
        if (index != -1) {
            // Replace the task at the index with the updated task
            tasks[index] = task
        }
    }
    /**
     * Get a task from the list by ID.
     * @param id the ID of the task to get
     * @return the task with the given ID
     */
    fun getTask(id: Long): Task {
        // Find the task with the given ID using the first() function to return the first task
        // that matches the predicate: it.taskId == id
        return tasks.first { it.taskId == id }
    }
}
