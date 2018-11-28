package com.orego.battlecrane.bcApi.manager

import com.orego.battlecrane.bcApi.manager.battlefield.BBattleMap
import com.orego.battlecrane.bcApi.scenario.BGameScenario

class BGameManager(scenario: BGameScenario) {

    val battleMap = BBattleMap(scenario)
}