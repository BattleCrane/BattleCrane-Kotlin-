package com.orego.battlecrane.core

import android.app.Application
import com.orego.battlecrane.repository.game.manager.BManager

class BApplication : Application() {

    val manager = BManager()

    var activityController: BActivity.BActivityController? = null

    override fun onCreate() {
        super.onCreate()
    }
}