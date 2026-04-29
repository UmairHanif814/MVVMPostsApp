package com.example.systemassesment.data.mapper

import com.example.systemassesment.data.local.entity.PostEntity
import com.example.systemassesment.data.model.Comment
import com.example.systemassesment.data.model.Post
import com.example.systemassesment.data.remote.dto.CommentDto
import com.example.systemassesment.data.remote.dto.PostDto

fun PostDto.toEntity(existingFavorite: Boolean) = PostEntity(
    userId = userId,
    id = id,
    title = title,
    body = body,
    isFavorite = existingFavorite
)

fun PostEntity.toModel() = Post(
    userId = userId,
    id = id,
    title = title,
    body = body,
    isFavorite = isFavorite
)

fun CommentDto.toModel() = Comment(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)
