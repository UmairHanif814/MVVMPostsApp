package com.example.systemassesment.data.repository

import com.example.systemassesment.data.model.Comment
import com.example.systemassesment.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun observePosts(): Flow<List<Post>>
    fun observeFavoritePosts(): Flow<List<Post>>
    suspend fun refreshPosts(): Result<Unit>
    suspend fun toggleFavorite(postId: Int): Result<Unit>
    suspend fun getComments(postId: Int): Result<List<Comment>>
}
