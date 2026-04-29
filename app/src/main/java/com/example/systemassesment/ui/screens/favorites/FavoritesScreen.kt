package com.example.systemassesment.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.systemassesment.ui.components.EmptyFavoritesState
import com.example.systemassesment.ui.components.ErrorView
import com.example.systemassesment.ui.components.LoadingView
import com.example.systemassesment.ui.components.PostCard
import com.example.systemassesment.utils.UiState

@Composable
fun FavoritesRoute(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    FavoritesScreen(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onEvent: (FavoritesEvent) -> Unit
) {
    when (val uiState = state.uiState) {
        UiState.Loading -> LoadingView()
        is UiState.Error -> ErrorView(message = uiState.message)
        is UiState.Success -> {
            if (uiState.data.isEmpty()) {
                EmptyFavoritesState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(items = uiState.data, key = { it.id }) { post ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                if (
                                    value == SwipeToDismissBoxValue.EndToStart ||
                                    value == SwipeToDismissBoxValue.StartToEnd
                                ) {
                                    onEvent(FavoritesEvent.RemoveFavorite(post.id))
                                    true
                                } else {
                                    false
                                }
                            }
                        )
                        SwipeToDismissBox(
                            state = dismissState,
                            enableDismissFromStartToEnd = true,
                            enableDismissFromEndToStart = true,
                            modifier = Modifier.fillMaxWidth(),
                            backgroundContent = {}
                        ) {
                            PostCard(post = post, onClick = { onEvent(FavoritesEvent.RemoveFavorite(post.id)) })
                        }
                    }
                }
            }
        }
    }
}
