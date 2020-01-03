package com.heinika.github.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(@PrimaryKey val email: String?, val avatarUrl: String?)