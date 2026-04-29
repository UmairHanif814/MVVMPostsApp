package com.example.systemassesment.ui.screens.favorites

import com.example.systemassesment.data.model.Post
import com.example.systemassesment.utils.UiState

data class FavoritesState(
    val uiState: UiState<List<Post>> = UiState.Loading
)

sealed interface FavoritesEvent {
    data class RemoveFavorite(val postId: Int) : FavoritesEvent
}
