package com.orego.battlecrane.ui.fragment.battle.mapRender.viewHolder

import android.view.View
import com.orego.battlecrane.bcApi.unit.BUnit

abstract class BUnitViewHolder<T : BUnit>(val unit: T) {

    abstract val displayedView: View
}