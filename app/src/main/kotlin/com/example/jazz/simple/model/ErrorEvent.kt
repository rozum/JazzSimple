package com.example.jazz.simple.model

sealed interface ErrorEvent {

    sealed interface SmallErrors {

    }

    /**
     * Ошибки приводящие к завершению приложения
     */
    sealed interface TerminateErrorEvent : ErrorEvent {
        data class HttpError(val code: Int) : TerminateErrorEvent
        object NoNetworkConnection : TerminateErrorEvent
    }
}
