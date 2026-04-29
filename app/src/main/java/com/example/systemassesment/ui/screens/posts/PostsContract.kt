package com.example.systemassesment.ui.screens.posts

import com.example.systemassesment.data.model.Comment
import com.example.systemassesment.data.model.Post
import com.example.systemassesment.utils.UiState

data class PostsState(
    val uiState: UiState<List<Post>> = UiState.Loading,
    val isRefreshing: Boolean = false,
    val commentsUiState: UiState<List<Comment>>? = null,
    val selectedPostTitle: String = ""
)

sealed interface PostsEvent {
    data object Refresh : PostsEvent
    data object Retry : PostsEvent
    data class ToggleFavorite(val postId: Int) : PostsEvent
    data class ViewComments(val postId: Int, val title: String) : PostsEvent
    data object DismissComments : PostsEvent
}

sealed interface PostsEffect {
    data class ShowError(val message: String) : PostsEffect
    data object ShowNoInternet : PostsEffect
}
