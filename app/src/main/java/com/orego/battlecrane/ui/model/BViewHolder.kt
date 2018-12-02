package com.orego.battlecrane.ui.model

import android.view.View

abstract class BViewHolder<T>(val entity: T) {

    abstract val displayedView: View
}