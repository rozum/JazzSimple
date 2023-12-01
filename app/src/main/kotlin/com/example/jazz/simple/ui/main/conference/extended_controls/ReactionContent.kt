package com.example.jazz.simple.ui.main.conference.extended_controls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.tooling.preview.Preview
import com.example.jazz.simple.model.EmojiReaction

@Composable
@Preview(widthDp = 375)
internal fun ReactionsContent(
    reactions: List<EmojiReaction> = EmojiReaction.entries,
    enabled: Boolean = true,
    onClick: (ReactionClickCallback) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.56f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        reactions.forEach { reaction ->
            var position by remember { mutableStateOf(0f) }
            var width by remember { mutableStateOf(0) }
            EmojiView(
                code = reaction.code,
                modifier = Modifier
                    .onGloballyPositioned {
                        position = it.positionInWindow().x
                        width = it.size.width
                    }
                    .clickable { onClick(ReactionClickCallback(reaction, width, position)) },
            )
        }
    }
}

val EmojiReaction.code
    get() = when (this) {
        EmojiReaction.APPLAUSE -> "\uD83D\uDD25"
        EmojiReaction.LIKE -> "\uD83D\uDC4D"
        EmojiReaction.DISLIKE -> "\uD83D\uDC4E"
        EmojiReaction.SMILE -> "\uD83D\uDE02"
        else -> "\uD83D\uDE2E"
    }

private val EmojiReaction.description: String
    get() = when (this) {
        EmojiReaction.APPLAUSE -> "Аплодисменты"
        EmojiReaction.LIKE -> "Палец вверх"
        EmojiReaction.DISLIKE -> "Палец вниз"
        EmojiReaction.SMILE -> "Смех"
        else -> "Удивление"
    }

data class ReactionClickCallback(
    val reactionItem: EmojiReaction,
    val width: Int,
    val offsetInWindow: Float
)
