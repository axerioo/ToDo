package com.example.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.todo.data.Task
import kotlinx.coroutines.flow.Flow

/**
 * Data access object for the [ToDoDatabase].
 */
@Dao
interface ToDoDao {
    /**
     * Insert a task into the database.
     * If the task already exists, replace it ([OnConflictStrategy.REPLACE]).
     * @param task the task to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    /**
     * Update a task in the database.
     * @param task the task to update
     */
    @Update
    suspend fun updateTask(task: Task)

    /**
     * Delete a task from the database.
     * @param task the task to delete
     */
    @Delete
    suspend fun deleteTask(task: Task)

    /**
     * Get all tasks from the database. The returned list of tasks is a [Flow] that will be
     * automatically updated when the underlying data changes and can trigger recompositions.
     * @return a [Flow] of list of tasks
     */
    @Query("SELECT * FROM todo_tasks")
    // @QUERY annotation specifies the SQL query to get all tasks (*) from the todo_tasks table
    fun getAllTasks(): Flow<List<Task>>

    /**
     * Get a task by its ID from the database. The returned task is a [Flow] so that it can be
     * observed for changes and trigger recompositions.
     * @param taskId the ID of the task to get
     * @return a [Flow] of a task
     */
    @Query("SELECT * FROM todo_tasks WHERE taskId = :taskId")
    // WHERE in the SQL query allows to filter the tasks by the taskId column. The :taskId is a
    // placeholder that will be replaced by the actual taskId value when the query is executed.
    fun getTaskById(taskId: Long): Flow<Task?>
}