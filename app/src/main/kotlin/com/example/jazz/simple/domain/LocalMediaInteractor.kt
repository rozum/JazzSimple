package com.example.jazz.simple.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LocalMediaInteractor {

    private val _mediaStateFlow = MutableStateFlow(MediaState())
    val mediaStateFlow: StateFlow<MediaState> = _mediaStateFlow.asStateFlow()

    /**
     * Переключение состояния камеры.
     */
    fun switchCamera() {
        _mediaStateFlow.update { it.copy(isCamEnabled = !it.isCamEnabled) }
    }

    /**
     * Переключение состояния микрофона.
     */
    fun switchMic() {
        _mediaStateFlow.update { it.copy(isMicEnabled = !it.isMicEnabled) }
    }

    data class MediaState(
        val isCamEnabled: Boolean = false,
        val isMicEnabled: Boolean = false
    )
}