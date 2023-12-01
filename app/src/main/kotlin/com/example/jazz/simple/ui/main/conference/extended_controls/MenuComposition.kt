package com.example.jazz.simple.ui.main.conference.extended_controls

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.jazz.simple.R
import com.example.jazz.simple.ui.core.BottomSheetMenuItemComposition
import com.example.jazz.simple.ui.main.conference.extended_controls.ExtendedControlsViewModel.LoadedMenuItem
import com.example.jazz.simple.ui.main.conference.extended_controls.ExtendedControlsViewModel.MenuItem

@Composable
fun MenuComposition(
    menu: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit,
) {
    Column {
        for (menuItem in menu) {
            BottomSheetMenuItemComposition(
                imageResId = menuItem.imageResId,
                text = menuItem.text,
                inProgress = (menuItem as? LoadedMenuItem)?.isInProgress == true,
            ) { onItemClick(menuItem) }
        }
    }
}

private val MenuItem.text: String
    get() = when (this) {
        is MenuItem.Asr -> when (enabled) {
            true -> "Выключить расшифровку встречи"
            false -> "Включить расшифровку встречи"
        }

        is MenuItem.ConferenceTransfer -> "Перенести на устройство"
        is MenuItem.HandRaised -> "Открепить участника"
        is MenuItem.Invite -> "Пригласить участников"
        is MenuItem.PinParticipant -> when (enabled) {
            true -> "Открепить участника"
            false -> "Записать встречу на сервер"
        }

        is MenuItem.Recording -> when (enabled) {
            true -> "Остановить запись"
            false -> "Записать встречу на сервер"
        }

        is MenuItem.ShowParticipants -> "Показать всех участников"
        is MenuItem.TilesMode -> "Показать активно говорящего"
        is MenuItem.ScreenShare -> when (enabled) {
            true -> "Остановить демонстрацию"
            false -> "Поделиться экраном"
        }
    }

private val MenuItem.imageResId: Int
    get() = when (this) {
        is MenuItem.Asr -> R.drawable.ic_asr
        is MenuItem.ConferenceTransfer -> R.drawable.ic_transfer
        is MenuItem.HandRaised -> R.drawable.ic_raise_hand
        is MenuItem.Invite -> R.drawable.ic_invite
        is MenuItem.PinParticipant -> R.drawable.ic_unpin
        is MenuItem.Recording -> when (enabled) {
            true -> R.drawable.ic_record_on
            false -> R.drawable.ic_record_off
        }

        is MenuItem.ShowParticipants -> R.drawable.ic_group
        is MenuItem.TilesMode -> R.drawable.ic_full_screen
        is MenuItem.ScreenShare -> R.drawable.ic_share_screen
    }

@Composable
@Preview
private fun Preview() {
    val vm = ExtendedControlsViewModel()
    val state by vm.uiStateFlow.collectAsState()
    MenuComposition(state.menuItems) {}
}
