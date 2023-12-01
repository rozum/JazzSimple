package com.example.jazz.simple.domain

import android.content.Context
import com.example.jazz.simple.Application

object ServiceLocator {
    val applicationContext: Context = Application.instance
    val localMediaInteractor = lazy { LocalMediaInteractor() }
    val deviceInteractor = lazy { DeviceInteractor() }
    val errorInteractor = lazy { ErrorInteractor() }
    val signalingInteractor = lazy { SignalingInteractor() }
    val webinarInteractor = lazy { WebinarInteractor() }
    val applicationInteractor = lazy { ApplicationInteractor() }
    val deeplinkInteractor = lazy { DeeplinkInteractor() }
}