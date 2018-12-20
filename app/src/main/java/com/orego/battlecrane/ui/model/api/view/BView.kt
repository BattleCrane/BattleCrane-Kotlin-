package com.orego.battlecrane.ui.model.api.view

import android.view.View
import com.orego.battlecrane.ui.model.api.modeController.BClickController

abstract class BView<T : Any>(open val entity: T) {

    abstract val displayedView: View

    lateinit var clickController : BClickController

    open fun refresh() {}
}