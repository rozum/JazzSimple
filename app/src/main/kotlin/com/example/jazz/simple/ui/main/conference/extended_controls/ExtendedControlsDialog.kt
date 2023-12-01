package com.example.jazz.simple.ui.main.conference.extended_controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.R
import com.example.jazz.simple.model.EmojiReaction
import com.example.jazz.simple.ui.core.BottomSheetFrameView
import com.example.jazz.simple.ui.core.BottomSheetHeaderComposition
import com.example.jazz.simple.ui.core.CloseDialogHeaderButton
import com.example.jazz.simple.ui.core.DialogHeaderButton
import com.example.jazz.simple.ui.theme.BottomSheetBackground
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(widthDp = 375, heightDp = 700)
fun ExtendedControlsDialog(
    vm: ExtendedControlsViewModel = viewModel(),
    onDismiss: () -> Unit = {},
) {
    val state by vm.uiStateFlow.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()

    val close = fun() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onDismiss()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.Transparent,
        dragHandle = {},
    ) {
        Column {
            DialogHeader(
                onCloseClick = { close() },
                OnSettingsClick = vm::onSettingsClick,
                OnReactionClick = vm::onReactionClick,
            )
            DialogBody(state) { menuItem ->
                vm.onMenuItemClick(menuItem)
                close()
            }
        }
    }
}

@Composable
private fun HeaderContainer(
    settingsButtonVisible: Boolean,
    onSettingsClick: () -> Unit,
    onCloseClick: () -> Unit,
) {
    BottomSheetHeaderComposition(
        dialogTitle = "Прочее",
        horizontalPadding = 0.dp,
    ) {
        if (settingsButtonVisible) {
            DialogHeaderButton(
                imageResId = R.drawable.ic_settings,
                contentDescription = null,
            ) {
                onSettingsClick()
            }
        }

        CloseDialogHeaderButton {
            onCloseClick()
        }
    }
}

@Composable
internal fun DialogBody(
    state: ExtendedControlsViewModel.UiState,
    onClick: (ExtendedControlsViewModel.MenuItem) -> Unit,
) {
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp)
            .background(BottomSheetBackground)
            .nestedScroll(rememberNestedScrollInteropConnection())
            .verticalScroll(rememberScrollState())
    ) {
        MenuComposition(state.menuItems, onItemClick = onClick)
    }
}

@Composable
internal fun DialogHeader(
    onCloseClick: () -> Unit = {},
    OnSettingsClick: () -> Unit = {},
    OnReactionClick: (reaction: EmojiReaction) -> Unit = {},
) {
    val reactionState = remember { mutableStateOf<ReactionElement?>(null) }
    val currentLocalDensity = LocalDensity.current
    DialogHeaderContainer(
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                HeaderContainer(
                    settingsButtonVisible = true,
                    onSettingsClick = OnSettingsClick,
                    onCloseClick = onCloseClick
                )
                Spacer(modifier = Modifier.height(30.dp))
                ReactionsContent() { clickEvent: ReactionClickCallback ->
                    val halfOfReactionWidthPx = clickEvent.width / 2
                    val position = with(currentLocalDensity) {
                        val offsetDp = clickEvent.offsetInWindow.toDp() + Random.nextInt(
                            -halfOfReactionWidthPx,
                            halfOfReactionWidthPx
                        ).dp
                        offsetDp.coerceAtLeast(0.dp)
                    }
                    reactionState.value = ReactionElement(
                        reactionCode = clickEvent.reactionItem.code,
                        x = position,
                    )
                    OnReactionClick(clickEvent.reactionItem)
                }
                Spacer(modifier = Modifier.height(21.dp))
            }
        },
        animationContent = {
            reactionState.value?.let { AnimationContent(it) }
        }
    )
}

@Composable
private fun DialogHeaderContainer(
    content: @Composable () -> Unit,
    animationContent: @Composable () -> Unit
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(52.dp))
            BottomSheetFrameView {
                Box(
                    modifier = Modifier.padding(start = 24.dp, top = 30.dp, end = 24.dp)
                ) {
                    content()
                }
            }
        }
        // зона анимации
        animationContent()
    }
}
