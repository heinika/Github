package com.heinika.github.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(@PrimaryKey val email: String, val avatarUrl: String?)