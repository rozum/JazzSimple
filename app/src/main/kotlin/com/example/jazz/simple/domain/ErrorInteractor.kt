package com.example.jazz.simple.domain

import com.example.jazz.simple.model.ErrorEvent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Класс, позволяющий все глобальные ошибки (на уровне приложения),
 * завернуть в единый поток, который доступен всем другим частям приложения.
 */
class ErrorInteractor {

    private val _errorFlow = MutableSharedFlow<ErrorEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val errorFlow: Flow<ErrorEvent> = _errorFlow.asSharedFlow()

    /**
     * Этим методом из любой части приложения можно инициировать обработку ошибки,
     * при этом вызывающий код никак не зависит от обработчика
     */
    fun emit(event: ErrorEvent) {
        _errorFlow.tryEmit(event)
    }
}