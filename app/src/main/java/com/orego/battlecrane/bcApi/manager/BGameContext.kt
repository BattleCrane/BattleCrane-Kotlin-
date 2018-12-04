package com.orego.battlecrane.bcApi.manager

import com.orego.battlecrane.bcApi.manager.mapManager.BMapManager
import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.bcApi.scenario.BGameScenario

class BGameContext(scenario: BGameScenario) {

    val mapManager = BMapManager(scenario, this)

    val playerManager = BPlayerManager(scenario)
}