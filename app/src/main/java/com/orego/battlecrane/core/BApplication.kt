package com.orego.battlecrane.core

import android.app.Application
import com.orego.battlecrane.repository.manager.BRepositoryManager

class BApplication : Application() {

    val manager = BRepositoryManager()

    var activityController: BActivity.BActivityController? = null

    override fun onCreate() {
        super.onCreate()
    }
}