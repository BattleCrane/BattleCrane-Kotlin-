package com.orego.battlecrane.bcApi.manager.playerManager.player

import com.orego.battlecrane.bcApi.bonus.BBonus
import com.orego.battlecrane.bcApi.unit.BUnit

abstract class BPlayer {

    val toolStack = ToolStack()

    class ToolStack {

        val buildingStack = mutableListOf<Class<out BUnit>>()

        val armyStack = mutableListOf<Class<out BUnit>>()

        val reinforcementStack = mutableListOf<Class<out BBonus>>()
    }
}