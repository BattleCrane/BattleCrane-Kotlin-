package com.orego.battlecrane.bcApi.manager.playerManager.team.player

import com.orego.battlecrane.bcApi.bonus.BBonus
import com.orego.battlecrane.bcApi.unit.BUnit

open class BPlayer {

    val toolStack = Tools()

    val alies = mutableSetOf<BPlayer>()

    val enemies = mutableSetOf<BPlayer>()

    class Tools {

        val buildingStack = mutableListOf<Class<out BUnit>>()

        val armyStack = mutableListOf<Class<out BUnit>>()

        val bonusStack = mutableListOf<Class<out BBonus>>()
    }
}