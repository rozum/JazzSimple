package com.example.jazz.simple.ui.main.stars

import android.app.Activity
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.max
import kotlin.math.min

class StarsViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    fun setRate(value: Int) {
        val filtered = min(0, max(5, value))
        _uiStateFlow.update { UiState(filtered) }
    }

    private fun sendRate() {
        // TODO: должен быть механизм отправки сразу после закрытия приложения, или
        //  не закрывать пока не отправим, но тогда что-то показать пользователю, чтобы не выглядело
        //  как заморозка.
    }

    fun onCloseClick(activity: Activity? = null) {
        sendRate()
        activity?.finish()
    }

    data class UiState(
        val rate: Int = 0
    )
}