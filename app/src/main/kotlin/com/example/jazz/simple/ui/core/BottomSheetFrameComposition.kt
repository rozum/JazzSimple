package com.example.jazz.simple.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jazz.simple.ui.theme.BottomSheetBackground

@Composable
fun BottomSheetFrameView(
    content: @Composable () -> Unit
) {
    Surface(
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White)
            )

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
                    .background(BottomSheetBackground)
            ) {
                content()
            }
        }
    }
}

@Composable
@Preview(widthDp = 375, heightDp = 700)
private fun Preview() {
    BottomSheetFrameView {}
}

