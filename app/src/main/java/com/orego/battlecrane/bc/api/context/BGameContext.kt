package com.orego.battlecrane.bc.api.context

import com.orego.battlecrane.bc.api.context.mapManager.BMapManager
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.scenario.BGameScenario

class BGameContext(scenario: BGameScenario) {

    /**
     * Pipeline.
     */

    val pipeline = BEventPipeline()

    /**
     * Player manager.
     */

    val playerManager = BPlayerManager(scenario, this)

    /**
     * Secondly -- map manager.
     */

    val mapManager = BMapManager(scenario, this)

    fun startGame() {
        this.playerManager.onGameStarted()
    }
}