package com.example.systemassesment.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.systemassesment.data.repository.AuthRepository
import com.example.systemassesment.utils.Validation
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

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect: SharedFlow<LoginEffect> = _effect.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.value,
                        isEmailValid = Validation.isValidEmail(event.value)
                    )
                }
            }

            is LoginEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.value,
                        isPasswordValid = Validation.isValidPassword(event.value)
                    )
                }
            }

            LoginEvent.Submit -> submit()
        }
    }

    private fun submit() {
        val currentState = _state.value
        if (!currentState.isSubmitEnabled) return
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true) }
            authRepository.setLoggedIn(true)
            _state.update { it.copy(isSubmitting = false) }
            _effect.emit(LoginEffect.NavigateToHome)
        }
    }
}
