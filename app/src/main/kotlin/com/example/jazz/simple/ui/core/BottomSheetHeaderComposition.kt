package com.example.jazz.simple.ui.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jazz.simple.R
import com.example.jazz.simple.ui.theme.White56
import com.example.jazz.simple.ui.theme.White96

@Composable
fun BottomSheetHeaderComposition(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 24.dp,
    dialogTitle: String,
    paneTitle: String? = dialogTitle,
    subTitle: @Composable () -> Unit = { },
    menu: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(
            horizontal = horizontalPadding
        )
    ) {
        Row(
            modifier = Modifier
                .semantics {
                    if (paneTitle != null) {
                        this.paneTitle = paneTitle
                    }
                },
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = dialogTitle,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = White96
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                menu()
            }
        }
        subTitle()
    }
}

@Composable
fun DialogHeaderButton(
    @DrawableRes imageResId: Int,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(40.dp)
            .padding(8.dp)
            .clickable(
                indication = rememberRipple(bounded = false),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        painter = painterResource(id = imageResId),
        contentDescription = contentDescription,
        tint = Color.Unspecified
    )
}

@Composable
fun CloseDialogHeaderButton(
    onClick: () -> Unit
) {
    DialogHeaderButton(
        imageResId = R.drawable.ic_close,
        contentDescription = null,
        onClick = onClick,
    )
}

@Preview(backgroundColor = 0xFF171717, showBackground = true)
@Composable
fun PreviewBottomSheetDialogHeader() {
    BottomSheetHeaderComposition(dialogTitle = "Заголовок") {
        CloseDialogHeaderButton { }
    }
}

@Preview(backgroundColor = 0xFF171717, showBackground = true)
@Composable
fun PreviewSubtitleBottomSheetDialogHeader() {
    BottomSheetHeaderComposition(
        dialogTitle = "Заголовок\nВторая строка",
        subTitle = {
            Text(
                text = "Проголосовали 12",
                color = White56,
            )
        }
    ) {
        DialogHeaderButton(
            imageResId = R.drawable.ic_search,
            contentDescription = null,
        ) { }
        CloseDialogHeaderButton { }
    }
}
