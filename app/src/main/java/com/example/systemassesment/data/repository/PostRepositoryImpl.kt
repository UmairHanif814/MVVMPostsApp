package com.example.systemassesment.data.repository

import com.example.systemassesment.data.local.datasource.PostLocalDataSource
import com.example.systemassesment.data.mapper.toModel
import com.example.systemassesment.data.mapper.toEntity
import com.example.systemassesment.data.model.Comment
import com.example.systemassesment.data.model.Post
import com.example.systemassesment.data.remote.datasource.PostRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) : PostRepository {
    override fun observePosts(): Flow<List<Post>> {
        return localDataSource.observePosts().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun observeFavoritePosts(): Flow<List<Post>> {
        return localDataSource.observeFavoritePosts().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun refreshPosts(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val remotePosts = remoteDataSource.getPosts()
            val mapped = remotePosts.map { dto ->
                val existing = localDataSource.getPostById(dto.id)
                dto.toEntity(existingFavorite = existing?.isFavorite ?: false)
            }
            localDataSource.upsertPosts(mapped)
        }
    }

    override suspend fun toggleFavorite(postId: Int): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val post = localDataSource.getPostById(postId) ?: return@runCatching
            localDataSource.updateFavorite(postId, !post.isFavorite)
        }
    }

    override suspend fun getComments(postId: Int): Result<List<Comment>> = withContext(Dispatchers.IO) {
        runCatching {
            remoteDataSource.getCommentsByPostId(postId).map { it.toModel() }
        }
    }
}
