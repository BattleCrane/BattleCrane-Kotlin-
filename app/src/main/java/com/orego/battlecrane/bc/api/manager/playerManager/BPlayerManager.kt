package com.orego.battlecrane.bc.api.manager.playerManager

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.api.model.unit.BUnit

class BPlayerManager(scenario: BGameScenario, gameContext: BGameContext) {

    val players : List<BPlayer> = scenario.initPlayerList(gameContext)

    var currentPlayer: BPlayer = scenario.getStartPlayer(this.players)

    fun isEnemies(unit1: BUnit, unit2: BUnit): Boolean {
        val owner1 = unit1.owner
        val owner2 = unit2.owner
        return owner1 != null && owner2 != null && owner1.isEnemy(owner2)
    }
}