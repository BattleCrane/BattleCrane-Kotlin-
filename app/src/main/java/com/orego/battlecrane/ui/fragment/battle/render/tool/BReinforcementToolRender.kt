package com.orego.battlecrane.ui.fragment.battle.render.tool

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import bcApi.unit.contract.BCoastable
import bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.tool.BToolViewHolder

class BReinforcementToolRender(
    private val playerManager: BPlayerManager,
    constraintLayout: ConstraintLayout,
    context: Context
) : BRender<BToolViewHolder>(constraintLayout, context) {

    private val reinforcementStack: List<BCoastable>
        get() = this.playerManager.currentPlayer.toolStack.reinforcementStack

    override fun draw() {

    }
}