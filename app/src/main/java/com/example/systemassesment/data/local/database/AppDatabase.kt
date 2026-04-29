package com.example.systemassesment.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.systemassesment.data.local.dao.PostDao
import com.example.systemassesment.data.local.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
