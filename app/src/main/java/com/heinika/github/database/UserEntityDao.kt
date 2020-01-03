package com.heinika.github.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query(value = "select * from user_table where email == :email ")
    fun getUserEntityByEmail(email: String): UserEntity
}