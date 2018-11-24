package com.orego.battlecrane.ui.fragment.battle.unit

import android.view.View

abstract class BUnitViewHolder<T>(val unit: T, buildView: () -> View) {

    val displayedView: View = buildView()
}