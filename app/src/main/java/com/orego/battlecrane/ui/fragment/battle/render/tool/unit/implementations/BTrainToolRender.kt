package com.orego.battlecrane.ui.fragment.battle.render.tool.unit.implementations

import bcApi.manager.playerManager.BPlayerManager
import bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.tool.unit.BUnitToolRender

class BTrainToolRender(private val playerManager: BPlayerManager) : BUnitToolRender() {

    override val stack: List<Class<out BUnit>>
        get() = this.playerManager.currentPlayer.toolStack.armyStack
}