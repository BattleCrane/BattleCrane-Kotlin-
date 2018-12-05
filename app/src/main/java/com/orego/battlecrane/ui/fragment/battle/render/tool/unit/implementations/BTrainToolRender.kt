package com.orego.battlecrane.ui.fragment.battle.render.tool.unit.implementations

import com.orego.battlecrane.bc.api.manager.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.tool.unit.BUnitToolRender

class BTrainToolRender(private val playerManager: BPlayerManager) : BUnitToolRender() {

    override val stack: List<Class<*>>
        get() = this.playerManager.currentPlayer.tools.armyStack
}