package com.example.jazz.simple.ui.main.conference.extended_controls

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
internal fun AnimationContent(
    reactionElement: ReactionElement,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val emojiTextSize = 36.sp
    val elements = remember { mutableStateOf(listOf<AnimationElement>()) }

    LaunchedEffect(key1 = reactionElement) {
        val element = AnimationElement(
            reactionElement = reactionElement,
            animation = Animatable(1f),
        )
        elements.value = elements.value.plus(element)
        scope.launch {
            element.animation.animateTo(0f, animationSpec = tween(2000))
            elements.value = elements.value.minus(element)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(ANIMATION_HEIGHT.dp)
    ) {
        elements.value.forEach { element ->
            EmojiView(
                code = element.reactionElement.reactionCode,
                modifier = Modifier
                    .padding(
                        start = element.reactionElement.x,
                        top = (element.animation.value * (ANIMATION_HEIGHT - emojiTextSize.value * 1.2)).dp
                    )
            )
        }
    }
}

private const val ANIMATION_HEIGHT = 160

data class ReactionElement(
    val reactionCode: String,
    val x: Dp,
)

private data class AnimationElement(
    val reactionElement: ReactionElement,
    val animation: Animatable<Float, AnimationVector1D>,
)

/**
 * Чтобы увидеть результат этого "просмотра" необходимо запускать
 * на устройстве или эмуляторе. В окне разработки анимация не воспроизводится.
 */
@Composable
@Preview(widthDp = 375, heightDp = 700)
private fun PreviewAnimationContent() {
    val reaction = remember { mutableStateOf(randomReactionElement("\uD83D\uDD25")) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimationContent(reaction.value)
        Row(Modifier.fillMaxWidth()) {
            listOf(
                "\uD83D\uDD25",
                "\uD83D\uDC4D",
                "\uD83D\uDC4E",
                "\uD83D\uDE02",
                "\uD83D\uDE2E",
            ).forEach {
                Button(
                    onClick = { reaction.value = randomReactionElement(it) },
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1f)
                ) {
                    Text(it)
                }
            }
        }
    }
}

private fun randomReactionElement(code: String): ReactionElement =
    ReactionElement(
        reactionCode = code,
        x = Random.nextInt(300).dp,
    )
