package com.example.jazz.simple.ui.main.signin

import androidx.lifecycle.ViewModel
import com.example.jazz.simple.domain.ServiceLocator
import com.example.jazz.simple.domain.SignalingInteractor
import com.example.jazz.simple.model.ConferenceCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    // @Inject
    private val signaling: SignalingInteractor by ServiceLocator.signalingInteractor

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    private val state: UiState
        get() = uiStateFlow.value

    fun setUserName(value: String) {
        _uiStateFlow.update { current ->
            current.copy(username = value)
        }
    }

    fun setUrl(value: String) {
        _uiStateFlow.update { current ->
            current.copy(url = value)
        }
    }

    fun setRoomCode(value: String) {
        _uiStateFlow.update { current ->
            current.copy(roomCode = value)
        }
    }

    fun setPassword(value: String) {
        _uiStateFlow.update { current ->
            current.copy(password = value)
        }
    }

    fun connect() {
        signaling.join(ConferenceCredential(state.url, state.username, state.password))
    }

    data class UiState(
        val username: String = "",
        val url: String = "",
        val roomCode: String = "",
        val password: String = "",
    )
}