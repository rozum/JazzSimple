package com.example.jazz.simple.ui.main.lobby

import androidx.lifecycle.ViewModel
import com.example.jazz.simple.domain.ServiceLocator
import com.example.jazz.simple.domain.SignalingInteractor

class LobbyViewModel : ViewModel() {

    // @Inject
    private val signaling: SignalingInteractor by ServiceLocator.signalingInteractor

    fun onCloseClick() {
        signaling.hangup()
    }
}