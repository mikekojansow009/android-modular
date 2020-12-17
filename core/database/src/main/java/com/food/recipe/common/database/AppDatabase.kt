package com.food.recipe.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.food.recipe.common.database.dao.UserDao
import com.food.recipe.common.database.model.User

/**
 * Created by mikekojansow on 18/08/20.
 * Senior Android Developer
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DB_NAME = "recipe_kitchn"
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (appDatabase != null) return appDatabase

            synchronized(AppDatabase::class) {
                appDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
            }

            return appDatabase
        }
    }
}