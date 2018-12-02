package bcApi.manager.playerManager

import bcApi.manager.playerManager.team.BTeam
import bcApi.manager.playerManager.team.player.BPlayer
import bcApi.scenario.BGameScenario
import bcApi.unit.BUnit

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