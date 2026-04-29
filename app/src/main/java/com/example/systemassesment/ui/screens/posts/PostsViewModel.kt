package com.example.systemassesment.ui.screens.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.systemassesment.data.repository.PostRepository
import com.example.systemassesment.utils.NetworkChecker
import com.example.systemassesment.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val networkChecker: NetworkChecker
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state: StateFlow<PostsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PostsEffect>()
    val effect: SharedFlow<PostsEffect> = _effect.asSharedFlow()

    init {
        observePosts()
        refresh()
    }

    fun onEvent(event: PostsEvent) {
        when (event) {
            PostsEvent.Refresh -> refresh()
            PostsEvent.Retry -> retry()
            is PostsEvent.ToggleFavorite -> toggleFavorite(event.postId)
            is PostsEvent.ViewComments -> {
                if (networkChecker.isConnected()){
                    loadComments(event.postId, event.title)
                }
                else{
                    viewModelScope.launch {
                        _effect.emit(PostsEffect.ShowNoInternet)
                    }
                }
            }
            PostsEvent.DismissComments -> {
                _state.update { it.copy(commentsUiState = null, selectedPostTitle = "") }
            }
        }
    }

    private fun observePosts() {
        viewModelScope.launch {
            postRepository.observePosts().collect { posts ->
                _state.update { it.copy(uiState = UiState.Success(posts)) }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            if (_state.value.uiState !is UiState.Success) {
                _state.update { it.copy(uiState = UiState.Loading) }
            }
            postRepository.refreshPosts().onFailure { throwable ->
                val fallbackState = _state.value.uiState
                if (fallbackState !is UiState.Success) {
                    _state.update { state ->
                        state.copy(uiState = UiState.Error(throwable.message ?: "Unknown error"))
                    }
                }
                if (throwable is UnknownHostException){
                    _effect.emit(PostsEffect.ShowError("No internet connection"))
                }
            }
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    private fun retry() {
        if (!networkChecker.isConnected()) {
            viewModelScope.launch {
                _effect.emit(PostsEffect.ShowNoInternet)
            }
            return
        }
        refresh()
    }

    private fun toggleFavorite(postId: Int) {
        viewModelScope.launch {
            postRepository.toggleFavorite(postId).onFailure { throwable ->
                _effect.emit(PostsEffect.ShowError(throwable.message ?: "Unable to update favorite"))
            }
        }
    }

    private fun loadComments(postId: Int, title: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    commentsUiState = UiState.Loading,
                    selectedPostTitle = title
                )
            }
            postRepository.getComments(postId)
                .onSuccess { comments ->
                    _state.update { it.copy(commentsUiState = UiState.Success(comments)) }
                }
                .onFailure { throwable ->
                    _state.update {
                        it.copy(commentsUiState = UiState.Error(throwable.message ?: "Unable to load comments"))
                    }
                }
        }
    }
}
