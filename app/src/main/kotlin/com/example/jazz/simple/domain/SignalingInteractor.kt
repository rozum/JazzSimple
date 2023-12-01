package com.example.jazz.simple.domain

import com.example.jazz.simple.domain.SignalingInteractor.SignalingState.Conference
import com.example.jazz.simple.domain.SignalingInteractor.SignalingState.Connecting
import com.example.jazz.simple.domain.SignalingInteractor.SignalingState.Disconnecting
import com.example.jazz.simple.domain.SignalingInteractor.SignalingState.Idle
import com.example.jazz.simple.domain.SignalingInteractor.SignalingState.Lobby
import com.example.jazz.simple.domain.SignalingInteractor.SignalingState.Webinar
import com.example.jazz.simple.model.ConferenceCredential
import com.example.jazz.simple.model.ErrorEvent
import com.example.jazz.simple.model.Participant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class SignalingInteractor(
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) : CoroutineScope by scope {

    // @Inject
    private val errorInteractor: ErrorInteractor by ServiceLocator.errorInteractor

    private val _signalingStateFlow = MutableStateFlow<SignalingState>(Idle)
    val signalingStateFlow: StateFlow<SignalingState> = _signalingStateFlow.asStateFlow()

    val state: SignalingState
        get() = signalingStateFlow.value

    val isActive: Boolean
        get() = state is Lobby || state is Webinar || state is Conference

    val credential: ConferenceCredential?
        get() = (state as? Lobby)?.credential
            ?: (state as? Webinar)?.credential
            ?: (state as? Conference)?.credential

    // это только для демонстрации разных кейсов
    private var count = 0

    fun join(credential: ConferenceCredential) {
        when (count++ % 3) {
            0 -> {
                // эмуляция присоединения к обычной конференции
                launch {
                    _signalingStateFlow.update { Connecting }
                    delay(1000)
                    _signalingStateFlow.update {
                        Conference(
                            credential = credential,
                            startedAt = System.currentTimeMillis(),
                            participants = EXAMPLE_PARTICIPANTS
                                .shuffled()
                                .map { name -> Participant(UUID.randomUUID().toString(), name) }
                        )
                    }
                }
            }

            1 -> {
                // каждый второй старт - эмуляция присоединения к конференции с лобби
                launch {
                    _signalingStateFlow.update { Connecting }
                    delay(1000)
                    _signalingStateFlow.update { Lobby() }
                    delay(5000)
                    _signalingStateFlow.update {
                        Conference(
                            credential = credential,
                            participants = EXAMPLE_PARTICIPANTS
                                .shuffled()
                                .map { name -> Participant(UUID.randomUUID().toString(), name) }
                        )
                    }
                }
            }

            2 -> {
                // каждый третий старт - эмуляция присоединения к вебинару
                launch {
                    _signalingStateFlow.update { Connecting }
                    delay(1000)
                    _signalingStateFlow.update { Webinar(credential = credential) }
                }
                // Сценарий ошибки через несколько сек
                launch {
                    delay(5_000)
                    errorInteractor.emit(ErrorEvent.TerminateErrorEvent.HttpError(403))
                    _signalingStateFlow.update { Idle }
                }
            }
        }
    }

    fun hangup() {
        // эмуляция завершения конференции
        coroutineContext.cancelChildren()
        launch {
            delay(1000)
            _signalingStateFlow.update { Disconnecting }
            delay(500)
            _signalingStateFlow.update { Idle }
        }
    }

    /**
     * Есть некоторые общие этапы, такие как Idle, Connecting, Disconnecting,
     * Но есть и индивидуальные, например, Lobby, Conference, Webinar. Причем лобби может перейти
     * в конференцию или в вебинар (чисто теоритически такое возможно) или конференция может стать
     * вебинаром из-за действий администратора, фактически это просто управление через бэк.
     * В данной архитектуре нет ограничений и стейты разных сущностей хранят разный набор данных.
     * При переключении вариантов данные предыдущего сбрасываются сами собой не надо чистить и
     * как-то дополнительно об этом заботиться. А если понадобится, то данные одной модели можно
     * передать в следующую с какой-то дополнительной логикой.
     */
    sealed interface SignalingState {

        object Idle : SignalingState
        object Connecting : SignalingState

        data class Lobby(
            val credential: ConferenceCredential? = null,
            val startedAt: Long = System.currentTimeMillis(),
            val participants: List<Participant> = emptyList()
        ) : SignalingState

        data class Webinar(
            val credential: ConferenceCredential? = null,
            val startedAt: Long = System.currentTimeMillis(),
            val participants: List<Participant> = emptyList()
        ) : SignalingState

        data class Conference(
            val credential: ConferenceCredential? = null,
            val startedAt: Long = System.currentTimeMillis(),
            val participants: List<Participant> = emptyList()
        ) : SignalingState

        object Disconnecting : SignalingState
    }

    companion object {
        val EXAMPLE_PARTICIPANTS = listOf(
            "Полиграф Шариков",
            "Филипп Филиппович",
            "Иван Борменталь",
            "Клим Чугункин",
            "Зинаида Бунина"
        )
    }
}