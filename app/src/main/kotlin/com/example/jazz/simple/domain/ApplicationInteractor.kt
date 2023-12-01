package com.example.jazz.simple.domain

import android.content.Intent
import android.content.pm.PackageManager
import com.example.jazz.simple.Application

class ApplicationInteractor {

    fun restartApplication() {
        val context = Application.instance.applicationContext
        val packageManager: PackageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        mainIntent.setPackage(context.packageName)
        context.startActivity(mainIntent)
    }
}