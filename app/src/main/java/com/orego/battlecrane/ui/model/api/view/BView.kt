package com.orego.battlecrane.ui.model.api.view

import android.view.View

abstract class BView<T : Any>(val entity: T) {

    abstract val displayedView: View
}