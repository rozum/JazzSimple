package com.example.jazz.simple.ui.main.conference.extended_controls

import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal fun EmojiView(code: String, modifier: Modifier = Modifier) {
    val textSize = 36.sp
    AndroidView(
        factory = { context ->
            val layoutParams = LinearLayoutCompat.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            AppCompatTextView(context).apply {
                setLayoutParams(layoutParams)
                this.textSize = textSize.value
                setTextColor(Color.Black.toArgb())
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        },
        modifier = modifier,
        update = {
            it.text = code
        },
    )
}

@Preview
@Composable
private fun EmojiViewPreview() {
    EmojiView(code = "\uD83D\uDD25")
}

@Preview
@Composable
private fun EmojiViewDisabledPreview() {
    EmojiView(
        code = "\uD83D\uDD25",
        modifier = Modifier.alpha(0.56f)
    )
}
