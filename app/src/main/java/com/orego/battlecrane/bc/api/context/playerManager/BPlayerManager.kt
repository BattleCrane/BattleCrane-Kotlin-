package com.orego.battlecrane.bc.api.context.playerManager

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.api.scenario.BGameScenario

class BPlayerManager(scenario: BGameScenario, context: BGameContext) {

    val players : List<BPlayer> = scenario.initPlayerList(context)

    var currentPlayer: BPlayer = scenario.getStartPlayer(this.players)

    fun isEnemies(unit1: BUnit, unit2: BUnit): Boolean {
        val owner1 = unit1.owner
        val owner2 = unit2.owner
        return owner1 != null && owner2 != null && owner1.isEnemy(owner2)
    }
}