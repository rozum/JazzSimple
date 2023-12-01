package com.example.jazz.simple.ui.main.error

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
fun NoBackwardCompatibilityComposition(vm: ErrorViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ой", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("Приложение устарело", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(
            "Данный функционал не доступен для этой версии. Пора обновить приложение.",
            modifier = Modifier.padding(horizontal = 20.dp), textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(32.dp))
        Button(onClick = vm::onUpdateClick) {
            Text("Обновить")
        }
        Button(
            onClick = vm::onHomeClick,
            colors = buttonColors(containerColor = colorScheme.secondary)
        ) {
            Text("Закрыть")
        }
    }
}