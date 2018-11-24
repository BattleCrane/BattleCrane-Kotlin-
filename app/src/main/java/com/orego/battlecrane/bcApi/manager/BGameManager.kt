package com.orego.battlecrane.bcApi.manager

import com.orego.battlecrane.bcApi.manager.battlefield.BBattleField
import com.orego.battlecrane.bcApi.scenario.BGameScenario

class BGameManager(scenario: BGameScenario) {

    val battleField = BBattleField(scenario)
}