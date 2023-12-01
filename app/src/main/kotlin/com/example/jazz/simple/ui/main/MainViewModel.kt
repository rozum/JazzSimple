package com.example.jazz.simple.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jazz.simple.domain.DeeplinkInteractor.DeeplinkState
import com.example.jazz.simple.domain.ServiceLocator
import com.example.jazz.simple.domain.SignalingInteractor
import com.example.jazz.simple.model.ErrorEvent
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Conference
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Loader
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Lobby
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Stars
import com.example.jazz.simple.ui.main.MainViewModel.Mode.TerminateError
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Webinar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

/**
 * Представляет верхне-уровневую абстракцию отображения приложения с одним активити.
 * Данная вьюмодель реализует логику переключения базовых сущностей:
 */
class MainViewModel : ViewModel() {

    // @Inject - Dagger позволяет инжектировать зависимости аннотациями без конструкторов,
    // это если нужно, но в этом примере без усложнения
    private val errorInteractor by ServiceLocator.errorInteractor
    private val signaling by ServiceLocator.signalingInteractor
    private val deeplinkInteractor by ServiceLocator.deeplinkInteractor

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    private val mode: Mode
        get() = uiStateFlow.value.mode

    init {
        errorInteractor.errorFlow
            .onEach(::onError)
            .launchIn(viewModelScope)
        signaling.signalingStateFlow
            .onEach(::signalingStateChanged)
            .launchIn(viewModelScope)
        deeplinkInteractor.deeplinkStateFlow
            .onEach(::deeplinkStateChanged)
            .launchIn(viewModelScope)
    }

    fun acceptDeeplink() {
        deeplinkInteractor.accept()
        _uiStateFlow.update { it.copy(isDeeplinkRequestVisible = false) }
    }

    fun rejectDeeplink() {
        deeplinkInteractor.reject()
        _uiStateFlow.update { it.copy(isDeeplinkRequestVisible = false) }
    }

    private fun onError(event: ErrorEvent) {
        when (event) {
            is ErrorEvent.TerminateErrorEvent -> onFatalError(event)
        }
    }

    private fun signalingStateChanged(event: SignalingInteractor.SignalingState) {
        when (event) {
            is SignalingInteractor.SignalingState.Idle -> Unit
            is SignalingInteractor.SignalingState.Connecting -> _uiStateFlow.update { UiState(Loader) }
            is SignalingInteractor.SignalingState.Lobby -> _uiStateFlow.update { UiState(Lobby) }
            is SignalingInteractor.SignalingState.Webinar -> _uiStateFlow.update { UiState(Webinar) }
            is SignalingInteractor.SignalingState.Conference -> _uiStateFlow.update { UiState(Conference) }
            is SignalingInteractor.SignalingState.Disconnecting -> _uiStateFlow.update { state ->
                if (state.mode !is TerminateError) {
                    UiState(Stars)
                } else {
                    state
                }
            }
        }
    }

    private fun onFatalError(reason: ErrorEvent.TerminateErrorEvent) {
        // отображаем ошибку только, если в текущий момент что-то кроме отображения ошибки
        if (mode !is TerminateError) {
            _uiStateFlow.update { UiState(TerminateError(reason)) }
        }
    }

    private fun deeplinkStateChanged(deeplinkState: DeeplinkState) {
        when (deeplinkState) {
            is DeeplinkState.Request -> {
                if (!signaling.isActive) {
                    deeplinkInteractor.accept()
                } else {
                    _uiStateFlow.update { it.copy(isDeeplinkRequestVisible = true) }
                }
            }

            is DeeplinkState.Actual -> {
                val credential = deeplinkInteractor.parse(deeplinkState.uri)
                if (credential != signaling.credential) {
                    signaling.join(credential)
                }
            }

            DeeplinkState.Empty -> Unit
        }
    }

    fun onBack() {
        //TODO("Not yet implemented")
    }

    data class UiState(
        val mode: Mode = Mode.SignIn,
        val isDeeplinkRequestVisible: Boolean = false
    )

    sealed interface Mode {

        object SignIn : Mode
        object Loader : Mode
        object Lobby : Mode
        object Webinar : Mode
        object Conference : Mode

        /**
         * Оценка
         */
        object Stars : Mode

        /**
         * Здесь передача управления дочернему компоненту, отвечающему за отображение терминальных ошибок.
         */
        data class TerminateError(val reason: ErrorEvent.TerminateErrorEvent) : Mode
    }
}