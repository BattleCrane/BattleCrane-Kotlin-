package com.orego.battlecrane.bc.api.model.adjutant;

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction;

open class BAdjutant(protected val context: BGameContext, protected val owner: BPlayer) {

    companion object {

        const val START_VALUE = 0
    }

    val buildingStack = mutableSetOf<BAction>()

    val armyStack = mutableSetOf<BAction>()

    val bonusStack = mutableSetOf<BAction>()

    var buildingAbilityCount = START_VALUE

    var trainAbilityCount = START_VALUE

    var energyCount: Int = START_VALUE

    open fun onStartGame() {

    }

    open fun onStartTurn() {
        this.energyCount++
    }

    open class Builder {

        open fun build(context: BGameContext, owner: BPlayer) = BAdjutant(context, owner)
    }
}