package com.orego.battlecrane.bc.api.context

import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.BStorage
import com.orego.battlecrane.bc.api.scenario.BGameScenario

/**
 * Game.
 */

@BContextComponent
class BGameContext(scenario: BGameScenario) {

    /**
     * Pipeline.
     */

    @BContextComponent
    val pipeline = BPipeline(this)

    /**
     * Map manager.
     */

    @BContextComponent
    val mapManager = BMapController(scenario, this)


    /**
     * Storage.
     */

    @BContextComponent
    val storage = BStorage(scenario, this)

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