package com.example.jazz.simple.ui.main.error

import androidx.lifecycle.ViewModel
import com.example.jazz.simple.domain.ApplicationInteractor
import com.example.jazz.simple.domain.ServiceLocator

class ErrorViewModel : ViewModel() {

    // @Inject
    private val applicationInteractor: ApplicationInteractor by ServiceLocator.applicationInteractor

    fun onHomeClick() {
        applicationInteractor.restartApplication()
    }

    fun onUpdateClick() {
    }
}
