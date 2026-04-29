package com.example.systemassesment.data.remote.datasource

import com.example.systemassesment.data.remote.api.PostApiService
import com.example.systemassesment.data.remote.dto.CommentDto
import com.example.systemassesment.data.remote.dto.PostDto
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val apiService: PostApiService
) {
    suspend fun getPosts(): List<PostDto> = apiService.getPosts()

    suspend fun getCommentsByPostId(postId: Int): List<CommentDto> = apiService.getCommentsByPostId(postId)
}
