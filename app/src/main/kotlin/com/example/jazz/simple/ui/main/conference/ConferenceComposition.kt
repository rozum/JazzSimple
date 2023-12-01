package com.example.jazz.simple.ui.main.conference

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.model.ScreenMode.FULLSCREEN
import com.example.jazz.simple.model.ScreenMode.TILES
import com.example.jazz.simple.ui.main.conference.chat.ChatComposition
import com.example.jazz.simple.ui.main.conference.controls.ControlsComposition
import com.example.jazz.simple.ui.main.conference.extended_controls.ExtendedControlsDialog
import com.example.jazz.simple.ui.main.conference.full_screen.FullScreenComposition
import com.example.jazz.simple.ui.main.conference.tiles_screen.TilesScreenComposition

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ConferenceComposition(vm: ConferenceViewModel = viewModel()) {

    val activity = LocalContext.current as? Activity
    val windowSizeClass = activity?.let { calculateWindowSizeClass(it) }
    val state by vm.uiStateFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        // тут можно учитывать и тип устройства и размер для мобилок (планшеты)
        // и можно учитывать даже раскладушки, но это потом)...
        if (vm.isMobile && windowSizeClass?.widthSizeClass == WindowWidthSizeClass.Compact) {
            CompactScreen(vm)
        } else {
            WideScreen(vm)
        }
    }

    // Один из вариантов хранения состояния ботом-шита в стейте вьюмодели как тут (один из!)
    // Это необязательно, можно просто добавить переменную
    //
    //      var isExtendedControlsVisible by remember { mutableStateOf(false) }
    //
    // и тогда состояние ботом-шита будет контролироваться внутри композ-функции изменением состояния
    // этой переменной, что допустимо, потому что никакой логики (бизнес) в этом событии нет,
    // кроме логики отображения
    if (state.isExtendedControlsVisible) {
        ExtendedControlsDialog {
            vm.hideExtendedControls()
            // для второго варианта:
            // isExtendedControlsVisible = false
        }
    }
}

@Composable
private fun BoxScope.CompactScreen(vm: ConferenceViewModel) {
    val state by vm.uiStateFlow.collectAsState()
    if (state.isChatVisible == true) {
        ChatComposition(modifier = Modifier.fillMaxSize())
    } else {
        val modifier = Modifier.fillMaxSize()
        when (state.screenMode) {
            FULLSCREEN -> FullScreenComposition(modifier)
            TILES -> TilesScreenComposition(modifier)
        }
        ControlsComposition(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun BoxScope.WideScreen(vm: ConferenceViewModel) {
    val state by vm.uiStateFlow.collectAsState()
    Row {
        if (state.isChatVisible != false) {
            ChatComposition(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight()
            )
        }
        Box(Modifier.weight(1f)) {
            val modifier = Modifier.fillMaxSize()
            when (state.screenMode) {
                FULLSCREEN -> FullScreenComposition(modifier)
                TILES -> TilesScreenComposition(modifier)
            }
            ControlsComposition(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Preview(device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PhoneConferencePreview() {
    ConferenceComposition()
}

@Preview(device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PhoneChatPreview() {
    val vm: ConferenceViewModel = viewModel()
    vm.switchChatVisible() // включаем чат
    ConferenceComposition(vm)
}

@Preview(device = Devices.NEXUS_10, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TabletLandscapePreview() {
    val vm: ConferenceViewModel = viewModel()
    val state by vm.uiStateFlow.collectAsState()
    // экранируем логику, чтобы чат был всегда виден
    if (state.isChatVisible != true) {
        vm.switchChatVisible()
    }
    ConferenceComposition(vm)
}
