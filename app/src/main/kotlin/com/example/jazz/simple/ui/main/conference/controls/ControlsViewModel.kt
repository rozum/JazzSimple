package com.example.jazz.simple.ui.main.conference.controls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jazz.simple.domain.ServiceLocator
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ControlsViewModel : ViewModel() {

    // @Inject
    private val localMedia by ServiceLocator.localMediaInteractor
    private val signaling by ServiceLocator.signalingInteractor

    val uiStateFlow: StateFlow<UiState> =
        localMedia.mediaStateFlow
            .map { mediaState ->
                UiState(
                    isMicEnabled = mediaState.isMicEnabled,
                    isCamEnabled = mediaState.isCamEnabled,
                )
            }
            .stateIn(viewModelScope, WhileSubscribed(), UiState())

    fun onMicClick() {
        localMedia.switchMic()
    }

    fun onCallClick() {
        signaling.hangup()
    }

    fun onCamClick() {
        localMedia.switchCamera()
    }

    data class UiState(
        val isMicEnabled: Boolean = false,
        val isCamEnabled: Boolean = false
    )
}