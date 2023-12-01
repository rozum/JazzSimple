package com.example.jazz.simple.ui.main.conference.controls

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.R
import com.example.jazz.simple.ui.main.conference.ConferenceViewModel

/**
 * @param conferenceViewModel Вьюмодель базовой композиции. Требуется передать события
 * отображения чата и диалога "extended controls" базовому отображению. Это его зона
 * ответственности. Это можно сделать используя общую вьюмодель или явно пробрасывая
 * колбеки. Здесь используем первый вариант, просто он более компактный.
 */
@Composable
@Preview(widthDp = 375, backgroundColor = 0xFF888888)
fun ControlsComposition(
    modifier: Modifier = Modifier,
    vm: ControlsViewModel = viewModel(),
    conferenceViewModel: ConferenceViewModel = viewModel(),
) {
    val state by vm.uiStateFlow.collectAsState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ControlButton(R.drawable.ic_chat, onClick = conferenceViewModel::switchChatVisible)

        when (state.isMicEnabled) {
            true -> ControlButton(R.drawable.ic_mic, onClick = vm::onMicClick)
            else -> ControlButton(R.drawable.ic_mic_off, Color.Red, onClick = vm::onMicClick)
        }

        ControlButton(R.drawable.ic_call_end, ovalColor = Color.Red, onClick = vm::onCallClick)

        when (state.isCamEnabled) {
            true -> ControlButton(R.drawable.ic_cam, onClick = vm::onCamClick)
            else -> ControlButton(R.drawable.ic_cam_off, Color.Red, onClick = vm::onCamClick)
        }

        ControlButton(R.drawable.ic_more_horiz, onClick = conferenceViewModel::showExtendedControls)
    }
}

@Composable
@Preview
private fun ControlButton(
    @DrawableRes id: Int = R.drawable.ic_mic,
    tint: Color = Color.White,
    ovalColor: Color = Color(0xFF232323),
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color = ovalColor)
            .padding(12.dp)
            .clickable(onClick = onClick),
    ) {
        Image(
            painter = painterResource(id),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = tint)
        )
    }
}

@Composable
@Preview
private fun PreviewCallButton() {
    ControlButton(R.drawable.ic_call_end, ovalColor = Color.Red)
}
