package com.orego.battlecrane.ui.model.api.view

import android.view.View

abstract class BView<T : Any>(open val entity: T) {

    abstract val displayedView: View

    abstract fun refresh()
}