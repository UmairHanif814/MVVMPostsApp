package com.example.systemassesment.ui.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isSubmitting: Boolean = false
) {
    val isSubmitEnabled: Boolean
        get() = isEmailValid && isPasswordValid && !isSubmitting
}

sealed interface LoginEvent {
    data class EmailChanged(val value: String) : LoginEvent
    data class PasswordChanged(val value: String) : LoginEvent
    data object Submit : LoginEvent
}

sealed interface LoginEffect {
    data object NavigateToHome : LoginEffect
}
