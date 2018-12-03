package com.orego.battlecrane.ui.model.viewHolder

import android.view.View

abstract class BViewHolder<T : Any>(val entity: T) {

    abstract val displayedView: View
}