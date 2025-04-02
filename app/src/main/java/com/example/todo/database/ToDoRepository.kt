package com.example.todo.database

import com.example.todo.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class for the ToDoListApp
 * It provides an abstraction layer over the data source (the database)
 * and manages the data operations.
 * @param dao the data access object for the database
 */
class ToDoRepository(private val dao: ToDoDao) {
    /**
     * A flow of tasks from the database.
     * The flow will be automatically updated when the underlying data changes
     * and can trigger recompositions.
     */
    val tasksFlow = dao.getAllTasks()
    /**
     * Get a task [Flow] by its ID from the database.
     * @param taskId the ID of the task to get
     * @return a task [Flow]
     */
    fun getTaskById(taskId: Long) = dao.getTaskById(taskId)
    /**
     * Add a task to the database.
     * @param task the task to add
     */
    suspend fun addTask(task: Task) {
        // The operation is done in the IO dispatcher to avoid blocking the UI thread - IO
        // operations are optimized for disk and network operations.
        withContext(Dispatchers.IO) {
            dao.insertTask(task)
        }
    }
    /**
     * Update a task in the database.
     * @param task the task to update
     */
    suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO) {
            dao.updateTask(task)
        }
    }
    /**
     * Delete a task from the database.
     * @param task the task to delete
     */
    suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO) {
            dao.deleteTask(task)
        }
    }
}