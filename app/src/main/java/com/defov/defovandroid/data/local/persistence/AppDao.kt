package com.defov.defovandroid.data.local.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.defov.defovandroid.data.local.persistence.entity.UserEntity

@Dao
interface AppDao {
    @Query("SELECT * FROM user_entity WHERE user_id = :id")
    suspend fun getUserByID(id: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user_entity")
    suspend fun deleteAllUsers()
}