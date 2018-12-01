package com.orego.battlecrane.bcApi.manager

import com.orego.battlecrane.bcApi.manager.battleMapManager.BMapManager
import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.bcApi.scenario.BGameScenario

class BGameManager(scenario: BGameScenario) {

    val mapManager = BMapManager(scenario)

    val playerManager = BPlayerManager(scenario)
}