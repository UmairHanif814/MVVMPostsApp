package com.example.systemassesment.data.local.datasource

import com.example.systemassesment.data.local.dao.PostDao
import com.example.systemassesment.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostLocalDataSource @Inject constructor(
    private val postDao: PostDao
) {
    fun observePosts(): Flow<List<PostEntity>> = postDao.observePosts()

    fun observeFavoritePosts(): Flow<List<PostEntity>> = postDao.observeFavoritePosts()

    suspend fun upsertPosts(posts: List<PostEntity>) = postDao.upsertPosts(posts)

    suspend fun getPostById(postId: Int): PostEntity? = postDao.getPostById(postId)

    suspend fun updateFavorite(postId: Int, isFavorite: Boolean) = postDao.updateFavorite(postId, isFavorite)
}
