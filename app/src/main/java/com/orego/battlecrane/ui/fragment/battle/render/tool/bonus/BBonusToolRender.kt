package com.orego.battlecrane.ui.fragment.battle.render.tool.bonus

import com.orego.battlecrane.bcApi.model.bonus.BBonus
import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.ui.fragment.battle.render.tool.BToolRender

class BBonusToolRender(private val playerManager: BPlayerManager) : BToolRender<BBonus>(COLUMN_COUNT, ROW_COUNT) {

    companion object {

        private const val COLUMN_COUNT = 5

        private const val ROW_COUNT = 5
    }

    override val stack: List<Class<out BBonus>>
        get() = this.playerManager.currentPlayer.tools.bonusStack
}