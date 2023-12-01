package com.example.jazz.simple.domain

import android.content.Intent
import android.net.Uri
import com.example.jazz.simple.model.ConferenceCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Реализует механизм принятия или отклонения входящих диплинков.
 * Если уже имееется текущий [Uri], то новый добавляется как запрос. В таком состоянии
 * интерактор может находиться сколько угодно до принятия решения вызовом любого из
 * методов [accept], [reject] или [reset].
 * Слушая состояние [deeplinkStateFlow] можно легко организовать любой интерфейс.
 * Во-первых, всегда имеем факт поступившего предложения, во-вторых можем принять или отклонить
 * действие в любой момент без организации сложных кейсов или даже проигнорировать очередное
 * предложение (поступивший диплинк).
 */
class DeeplinkInteractor {

    private val _deeplinkStateFlow = MutableStateFlow<DeeplinkState>(DeeplinkState.Empty)
    val deeplinkStateFlow: StateFlow<DeeplinkState> = _deeplinkStateFlow.asStateFlow()

    fun parse(uri: Uri): ConferenceCredential {
        // TODO: заглушка без реального разбора диплинка
        return ConferenceCredential(uri.toString(), "", "")
    }

    fun onIntent(intent: Intent) {
        val uri = intent.data ?: return
        _deeplinkStateFlow.update { state ->
            when {
                state is DeeplinkState.Empty ->
                    DeeplinkState.Request(current = Uri.EMPTY, offer = uri)

                state is DeeplinkState.Actual && uri != state.uri ->
                    DeeplinkState.Request(current = state.uri, offer = uri)

                state is DeeplinkState.Request && uri != state.current ->
                    DeeplinkState.Request(current = state.current, offer = uri)

                else -> state
            }
        }
    }

    fun accept() {
        _deeplinkStateFlow.update { state ->
            when (state) {
                is DeeplinkState.Request -> DeeplinkState.Actual(state.offer)
                else -> state
            }
        }
    }

    fun reject() {
        _deeplinkStateFlow.update { state ->
            when (state) {
                is DeeplinkState.Request -> DeeplinkState.Actual(state.current)
                else -> state
            }
        }
    }

    fun reset() {
        _deeplinkStateFlow.update { DeeplinkState.Empty }
    }

    sealed interface DeeplinkState {
        object Empty : DeeplinkState
        data class Actual(val uri: Uri) : DeeplinkState
        data class Request(val current: Uri, val offer: Uri) : DeeplinkState
    }
}