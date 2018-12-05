package com.orego.battlecrane.ui.fragment.battle.render.tool.bonus

import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.manager.playerManager.BPlayerManager
import com.orego.battlecrane.ui.fragment.battle.render.tool.BToolRender

class BBonusToolRender(private val playerManager: BPlayerManager) : BToolRender(COLUMN_COUNT, ROW_COUNT) {

    companion object {

        private const val COLUMN_COUNT = 5

        private const val ROW_COUNT = 5
    }

    override val stack: List<Class<out BAction>>
        get() = this.playerManager.currentPlayer.tools.bonusStack
}