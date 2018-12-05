package com.orego.battlecrane.bc.api.manager

import com.orego.battlecrane.bc.api.manager.mapManager.BMapManager
import com.orego.battlecrane.bc.api.manager.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.scenario.BGameScenario

class BGameContext(scenario: BGameScenario) {

    val mapManager = BMapManager(scenario, this)

    val playerManager = BPlayerManager(scenario)
}