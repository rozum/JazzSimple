package com.example.jazz.simple.ui.main.conference.extended_controls

import androidx.lifecycle.ViewModel
import com.example.jazz.simple.model.EmojiReaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExtendedControlsViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    fun onSettingsClick() {
    }

    fun onReactionClick(reaction: EmojiReaction) {
    }

    fun onMenuItemClick(menuItem: MenuItem) {
    }

    data class UiState(
        val menuItems: List<MenuItem> = listOf(
            MenuItem.HandRaised,
            MenuItem.ShowParticipants,
            MenuItem.TilesMode(false),
            MenuItem.Invite,
            MenuItem.PinParticipant(false),
            MenuItem.Asr(false),
            MenuItem.Recording(false),
            MenuItem.ConferenceTransfer,
            MenuItem.ScreenShare(false),
        )
    )

    interface LoadedMenuItem {
        val isInProgress: Boolean
    }

    sealed interface MenuItem {

        object HandRaised : MenuItem
        object ShowParticipants : MenuItem
        data class TilesMode(val enabled: Boolean) : MenuItem
        object Invite : MenuItem
        data class PinParticipant(val enabled: Boolean) : MenuItem
        data class Asr(val enabled: Boolean) : MenuItem
        data class Recording(val enabled: Boolean) : MenuItem
        object ConferenceTransfer : MenuItem
        data class ScreenShare(val enabled: Boolean) : MenuItem
    }
}