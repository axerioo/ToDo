package com.example.todo.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.todo.data.Task

/**
 * The Room database for this app. It is a singleton that provides a database instance.
 * This code is a standard way to create a Room database and is often reused in other apps.
 * @Database annotation specifies the entities (tables in the database) and the version of the database.
 * When you change the entity class you should update the version number.
 */
@Database(entities = [Task::class], version = 1)
abstract class ToDoDatabase() : RoomDatabase() {
    // The access to the data is done through the DAO (Data Access Object) interface.
    abstract fun dao(): ToDoDao

    /**
     * A companion object to create the database instance.
     * The companion object is a singleton that is created when the class is loaded.
     * It is used to create the database instance and make sure that only one instance is created.
     */
    companion object {
        // The name of the database
        const val DATABASE_NAME = "todo_database"

        // Variable to hold the database instance. The @Volatile annotation means that the access
        // to the variable is always atomic and the write operations are
        // done in such a way that it is visible to all threads.
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        /**
         * Get the database instance.
         * @param context the application context - required to create the database
         * @return the database instance
         */
        fun getDatabase(context: Context): ToDoDatabase {
            // If the instance is null, create a new database instance in a synchronized way
            // to make sure that only one thread can access it at a time.
            return INSTANCE ?: synchronized(this) {
                // create a new database instance - this is a standard way to create a Room database
                Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
                // the also function is used to execute a block of code on the object that is being
                // created and return the object itself.
            }
        }
    }
}