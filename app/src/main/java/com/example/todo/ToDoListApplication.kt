package com.example.todo

import android.app.Application
import com.example.todo.database.ToDoDatabase
import com.example.todo.database.ToDoRepository

/**
 * Application class for the ToDoListApp
 * It initializes the ToDoRepository - the single source of truth for the app
 */
class ToDoListApplication: Application() {

    // The repository is initialized when the application is created
    // with the lateinit keyword to make sure it is initialized before it is used
    lateinit var toDoRepository: ToDoRepository
        private set // The setter is private to prevent other classes from modifying the repository

    override fun onCreate() {
        super.onCreate()
        // Initialize the repository by getting the instance of the database
        // and passing its dao to the repository
        toDoRepository = ToDoRepository(ToDoDatabase.getDatabase(this).dao())
    }
}
