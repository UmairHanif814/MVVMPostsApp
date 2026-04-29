package com.example.systemassesment.ui.screens.posts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.systemassesment.R
import com.example.systemassesment.ui.components.EmptyPostsState
import com.example.systemassesment.ui.components.ErrorView
import com.example.systemassesment.ui.components.LoadingView
import com.example.systemassesment.ui.components.PostCard
import com.example.systemassesment.utils.UiState

@Composable
fun PostsRoute(
    viewModel: PostsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PostsEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                PostsEffect.ShowNoInternet -> {
                    Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    PostsScreen(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    state: PostsState,
    onEvent: (PostsEvent) -> Unit
) {
    val commentsState = state.commentsUiState
    if (commentsState != null) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(PostsEvent.DismissComments) },

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = state.selectedPostTitle.ifEmpty { stringResource(id = R.string.comments) },
                    style = MaterialTheme.typography.titleMedium
                )
                when (commentsState) {
                    UiState.Loading -> LoadingView()
                    is UiState.Error -> ErrorView(message = commentsState.message)
                    is UiState.Success -> {
                        if (commentsState.data.isEmpty()) {
                            Text(text = stringResource(id = R.string.no_comments_found))
                        } else {
                            LazyColumn(
                                modifier = Modifier.heightIn(max = 420.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(commentsState.data, key = { it.id }) { comment ->
                                    Column {
                                        Text(text = comment.name)
                                        Text(text = comment.email, style = MaterialTheme.typography.bodySmall)
                                        Text(text = comment.body, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    when (val uiState = state.uiState) {
        UiState.Loading -> LoadingView()
        is UiState.Error -> {
            Column(modifier = Modifier.fillMaxSize()) {
                ErrorView(message = uiState.message)
                Button(
                    onClick = { onEvent(PostsEvent.Retry) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(text = "Retry")
                }
                if (state.isRefreshing) {
                    LoadingView()
                }
            }
        }
        is UiState.Success -> {
            if (state.isRefreshing && uiState.data.isEmpty()) {
                LoadingView()
            } else if (uiState.data.isEmpty()) {
                EmptyPostsState(){
                    onEvent(PostsEvent.Retry)
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (state.isRefreshing) {
                        LoadingView(modifier = Modifier.padding(top = 8.dp))
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(items = uiState.data, key = { it.id }) { post ->
                            PostCard(
                                post = post,
                                onClick = { onEvent(PostsEvent.ToggleFavorite(post.id)) },
                                onCommentsClick = { onEvent(PostsEvent.ViewComments(post.id, post.title)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
