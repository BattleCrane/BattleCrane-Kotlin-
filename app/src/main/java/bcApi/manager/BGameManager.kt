package bcApi.manager

import bcApi.manager.mapManager.BMapManager
import bcApi.manager.playerManager.BPlayerManager
import bcApi.scenario.BGameScenario

class BGameManager(scenario: BGameScenario) {

    val mapManager = BMapManager(scenario)

    val playerManager = BPlayerManager(scenario)
}