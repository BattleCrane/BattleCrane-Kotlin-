package com.orego.battlecrane.ui.model.viewHolder

import android.view.View

abstract class BViewHolder<T>(val entity: T) {

    abstract val displayedView: View
}