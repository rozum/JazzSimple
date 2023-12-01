package com.example.jazz.simple.ui.main.conference.tiles_screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TilesScreenComposition(modifier: Modifier = Modifier, vm: TilesScreenViewModel = viewModel()) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        for (i in 1..2) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                for (j in 1..2) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .background(Color(0xFF3737C8)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Здесь видео\n(пока не реализовано)",
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}

@Composable
@Preview(device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PhonePreview() {
    TilesScreenComposition(Modifier.fillMaxSize())
}

@Composable
@Preview(device = Devices.NEXUS_10, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun TabletLandscapePreview() {
    TilesScreenComposition(Modifier.fillMaxSize())
}
