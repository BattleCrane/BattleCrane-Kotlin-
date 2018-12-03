package com.orego.battlecrane.bcApi.manager.playerManager

import com.orego.battlecrane.bcApi.manager.playerManager.team.BTeam
import com.orego.battlecrane.bcApi.manager.playerManager.team.player.BPlayer
import com.orego.battlecrane.bcApi.scenario.BGameScenario
import com.orego.battlecrane.bcApi.unit.BUnit

class BPlayerManager(scenario: BGameScenario) {

    val currentPlayer: BPlayer
        get() = this.currentTeam.currentPlayer

    lateinit var currentTeam: BTeam

    val teams = mutableListOf<BTeam>()

    fun isEnemies(unit1: BUnit, unit2: BUnit): Boolean {
        val owner1 = unit1.owner
        val owner2 = unit2.owner
        return owner1 != null && owner2 != null && owner1.enemies.contains(owner2)
    }
}