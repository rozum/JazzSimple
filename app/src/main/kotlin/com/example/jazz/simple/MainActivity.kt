package com.example.jazz.simple

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jazz.simple.domain.ServiceLocator
import com.example.jazz.simple.ui.main.MainComposition

class MainActivity : ComponentActivity() {

    // @Inject
    private val deeplinkInteractor by ServiceLocator.deeplinkInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainComposition() }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { deeplinkInteractor.onIntent(it) }
    }
}
