package com.orego.battlecrane.bcApi.manager

import com.orego.battlecrane.bcApi.manager.battleMapManager.BBattleMapManager
import com.orego.battlecrane.bcApi.manager.playerManager.BPlayerManager
import com.orego.battlecrane.bcApi.scenario.BGameScenario

class BGameManager(scenario: BGameScenario) {

    val battleMap = BBattleMapManager(scenario)

    val playerManager = BPlayerManager(scenario)
}