package com.example.jazz.simple.ui.main.lobby

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.R

@Preview(name = "phone", device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "tablet", device = Devices.NEXUS_10, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LobbyComposition(vm: LobbyViewModel = viewModel()) {
    val activity = LocalContext.current as? Activity
    Box {
        Image(
            painter = painterResource(R.drawable.jazz),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            colorFilter = ColorFilter.tint(Color(0XFFD4CAA1))
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text("Зал ожидания", style = typography.titleLarge)
            Text("Администратор скоро вас пустит", style = typography.titleMedium)

            Button(onClick = {
                vm.onCloseClick()
                activity?.finish()
            }, content = {
                Text(text = "Выйти")
            })
        }
    }
}
