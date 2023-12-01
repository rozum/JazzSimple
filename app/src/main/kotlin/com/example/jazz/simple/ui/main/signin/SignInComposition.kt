package com.example.jazz.simple.ui.main.signin

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.R

@Preview(name = "phone", device = Devices.PIXEL, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "tablet", device = Devices.NEXUS_10, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SignInComposition(vm: SignInViewModel = viewModel()) {
    val state by vm.uiStateFlow.collectAsState()
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

            Field(value = state.username, onValueChange = vm::setUserName, label = "Имя пользователя")

            Field(value = state.url, onValueChange = vm::setUserName, label = "Домен")

            Field(value = state.roomCode, onValueChange = vm::setUserName, label = "Код")

            Field(value = state.password, onValueChange = vm::setUserName, label = "Пароль")

            Button(onClick = vm::connect, content = { Text(text = "Присоединиться") })
        }
    }
}

@Composable
private fun Field(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label ?: "", color = colorScheme.primary) },
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = colorScheme.primary,
            focusedTextColor = colorScheme.primary,
        ),
    )
}
