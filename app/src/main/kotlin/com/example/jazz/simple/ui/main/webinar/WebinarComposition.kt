package com.example.jazz.simple.ui.main.webinar

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
fun WebinarComposition(vm: WebinarViewModel = viewModel()) {
    val activity = LocalContext.current as? Activity
    val modifier = Modifier.fillMaxSize()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = modifier.weight(1f),
            painter = painterResource(R.drawable.webinar),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = {
                vm.onCloseClick()
                activity?.finish()
            },
            content = {
                Text(text = "Выйти")
            }
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}

