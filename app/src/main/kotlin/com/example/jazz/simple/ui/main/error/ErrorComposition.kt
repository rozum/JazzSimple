package com.example.jazz.simple.ui.main.error

import androidx.compose.runtime.Composable
import com.example.jazz.simple.model.ErrorEvent

@Composable
fun ErrorComposition(reason: ErrorEvent.TerminateErrorEvent) {
    when {
        reason is ErrorEvent.TerminateErrorEvent.HttpError && reason.code == 406 -> NoBackwardCompatibilityComposition()
        else -> DefaultErrorComposition()
    }
}