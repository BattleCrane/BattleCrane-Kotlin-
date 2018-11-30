package com.orego.battlecrane.ui.fragment.battle.holder

import android.view.View

abstract class BViewHolder<T>(val entity: T) {

    abstract val displayedView: View
}