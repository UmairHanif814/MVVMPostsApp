package com.example.systemassesment.data.remote.api

import com.example.systemassesment.data.remote.dto.CommentDto
import com.example.systemassesment.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsByPostId(@Path("postId") postId: Int): List<CommentDto>
}
