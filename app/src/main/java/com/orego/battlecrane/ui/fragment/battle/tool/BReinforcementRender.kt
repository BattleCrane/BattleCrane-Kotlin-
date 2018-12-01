package com.orego.battlecrane.ui.fragment.battle.tool

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bcApi.unit.contract.BCoastable
import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.fragment.battle.tool.viewHolder.BToolViewHolder

class BReinforcementRender(
    private val playerManager: BPlayerManager,
    constraintLayout: ConstraintLayout,
    context: Context
) : BRender<BToolViewHolder>(constraintLayout, context) {

    private val reinforcementStack: List<BCoastable>
        get() = this.playerManager.currentPlayer.toolStack.reinforcementStack

    override fun draw() {

    }
}