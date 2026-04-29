package com.example.systemassesment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    val userId: Int,
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val isFavorite: Boolean = false
)
