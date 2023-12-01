package com.example.jazz.simple.ui.main.stars

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.R

/**
 * Экран с оценкой
 */
@Composable
@Preview(device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun StarsComposition(vm: StarsViewModel = viewModel()) {

    val state by vm.uiStateFlow.collectAsState()
    val activity = LocalContext.current as? Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Оцените качество конференции",
            style = typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..5) {
                Image(
                    painter = painterResource(
                        when {
                            i <= state.rate -> R.drawable.ic_star_fill
                            else -> R.drawable.ic_star
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = { vm.setRate(i) }),
                    colorFilter = ColorFilter.tint(Color(0xFFF5BF4F))
                )
            }
        }
        Text(
            """
            Если у вас есть предложения по улучшению архитектуры данного примера,
            напишите мне на почту alexey.rozum@gmail.com"
        """.trimIndent(),
            style = typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Button(onClick = {
            vm.onCloseClick(activity)
        }) {
            Text(text = "Закрыть")
        }
    }
}