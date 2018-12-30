package com.orego.battlecrane.bc.api.context

import com.orego.battlecrane.bc.api.context.mapManager.BMapManager
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.scenario.BGameScenario

/**
 * Game.
 */

class BGameContext(scenario: BGameScenario) {

    /**
     * Pipeline.
     */

    val pipeline = BEventPipeline(this)

    /**
     * Player manager.
     */

    val playerManager = BPlayerManager(scenario, this)

    /**
     * Map manager.
     */

    val mapManager = BMapManager(scenario, this)

    /**
     * Launches a game.
     */

    fun startGame() {
        val event = OnGameStartedEvent()
        this.pipeline.pushEvent(event)
    }

    class OnGameStartedEvent : BEvent()

    class OnGameFinishedEvent : BEvent()
}