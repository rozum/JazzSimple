package com.example.jazz.simple.ui.main.webinar

import androidx.lifecycle.ViewModel
import com.example.jazz.simple.domain.ServiceLocator
import com.example.jazz.simple.domain.WebinarInteractor

class WebinarViewModel : ViewModel() {

    // @Inject
    private val webinar: WebinarInteractor by ServiceLocator.webinarInteractor

    fun onCloseClick() {
        webinar.hangup()
    }
}