package com.heinika.github.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class GithubDatabase : RoomDatabase() {

    abstract val userEntityDao: UserEntityDao

    companion object {

        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, GithubDatabase::class.java, "github_database")
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}