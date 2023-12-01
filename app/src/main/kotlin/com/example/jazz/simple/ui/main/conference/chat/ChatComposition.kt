package com.example.jazz.simple.ui.main.conference.chat

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatComposition(modifier: Modifier = Modifier, vm: ChatViewModel = viewModel()) {
    Box(
        modifier.background(Color(0xFFFFDD55)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Здесь контент чата\n(пока не реализовано)",
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun Preview() {
    ChatComposition(modifier = Modifier.fillMaxSize())
}
