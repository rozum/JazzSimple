package com.example.jazz.simple.ui.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jazz.simple.R
import com.example.jazz.simple.ui.theme.White96

@Composable
fun BottomSheetMenuItemComposition(
    @DrawableRes imageResId: Int,
    text: String,
    applyColorFilter: Boolean = true,
    inProgress: Boolean = false,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(
                enabled = !inProgress,
                onClick = onClick
            )
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp,
            )
            .indication(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = false)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (inProgress) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color(0xFF55585C),
                strokeWidth = 2.dp,
            )
        } else {
            ExtendedControlsImage(imageResId = imageResId, applyColorFilter = applyColorFilter)
        }

        val textModifier = if (inProgress) Modifier.alpha(0.4f) else Modifier
        ExtendedControlsText(
            text, modifier = Modifier
                .fillMaxWidth()
                .then(textModifier)
        )
    }
}

@Composable
private fun ExtendedControlsImage(imageResId: Int, applyColorFilter: Boolean = true) {
    Image(
        painter = painterResource(imageResId),
        contentDescription = null,
        modifier = Modifier.size(24.dp),
        colorFilter = if (applyColorFilter) {
            ColorFilter.tint(White96)
        } else {
            null
        },
    )
}

@Composable
private fun ExtendedControlsText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        color = White96,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Preview(showBackground = true, backgroundColor = 0x111111)
@Composable
private fun ChatSettingsDialogViewInProgressPreview() {
    BottomSheetMenuItemComposition(
        imageResId = R.drawable.ic_share_screen,
        text = "Поделиться экраном",
        inProgress = true,
    ) {}
}

@Preview(showBackground = true, backgroundColor = 0x111111)
@Composable
private fun ChatSettingsDialogViewPreview() {
    BottomSheetMenuItemComposition(
        imageResId = R.drawable.ic_share_screen,
        text = "Поделиться экраном",
    ) {}
}
