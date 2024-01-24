package com.example.jazz.simple.ui.main.conference

import androidx.lifecycle.ViewModel
import com.example.jazz.simple.domain.DeviceInteractor
import com.example.jazz.simple.model.ScreenMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConferenceViewModel : ViewModel() {

    // @Inject - Dagger позволяет инжектировать зависимости аннотациями без конструкторов,
    // это если нужно, но в этом примере без усложнения
    private val deviceInteractor: DeviceInteractor = DeviceInteractor()

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    val isMobile: Boolean = deviceInteractor.isMobile

    /**
     * Переключение (не установка enable/disable) потому, что интерфейс не должен
     * заботиться о текущем состоянии, это как раз ответственность этого класса
     */
    fun switchChatVisible() {
        _uiStateFlow.update { current ->
            current.copy(isChatVisible = !(current.isChatVisible ?: false))
        }
    }

    fun showExtendedControls() {
        _uiStateFlow.update { current ->
            current.copy(isExtendedControlsVisible = true)
        }
    }

    fun hideExtendedControls() {
        _uiStateFlow.update { current ->
            current.copy(isExtendedControlsVisible = false)
        }
    }

    data class UiState(
        val isChatVisible: Boolean? = null,  // если не задано для "узкого" будет скрыт, для "широкого" виден
        val isExtendedControlsVisible: Boolean = false,
        val isSettingsVisible: Boolean = false,
        val screenMode: ScreenMode = ScreenMode.FULLSCREEN,
    )
}
