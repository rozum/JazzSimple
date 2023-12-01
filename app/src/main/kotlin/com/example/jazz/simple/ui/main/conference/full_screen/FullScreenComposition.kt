package com.example.jazz.simple.ui.main.conference.full_screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.R

@Composable
fun FullScreenComposition(modifier: Modifier = Modifier, vm: FullScreenViewModel = viewModel()) {
    Box(modifier) {
        Image(
            painter = painterResource(R.drawable.example_camera_1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
@Preview(device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(device = Devices.NEXUS_10, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun Preview() {
    FullScreenComposition(Modifier.fillMaxSize())
}
