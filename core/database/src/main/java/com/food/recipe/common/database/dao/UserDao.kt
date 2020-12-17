package com.food.recipe.common.database.dao

import androidx.room.*
import com.food.recipe.common.database.model.User

/**
 * Created by mikekojansow on 18/08/20.
 * Senior Android Developer
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM `users` WHERE `id` = :userId LIMIT 1")
    suspend fun getUserBy(userId: String): User

    @Query("SELECT * FROM `users` LIMIT 1")
    suspend fun getFirstUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}