package com.orego.battlecrane.bc.api.context

import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.controller.player.BPlayerController
import com.orego.battlecrane.bc.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
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
     * Storage.
     */

    @BContextComponent
    val storage = BStorage(scenario, this)

    /**
     * Controller.
     */

    @BContextComponent
    val mapController = BMapController(this)

    @BContextComponent
    val playerController = BPlayerController(this, scenario.firstTurnPlayerPosition)

    /**
     * Id Generator.
     */

    val contextGenerator = BContextGenerator()

    /**
     * Launches a game.
     */

    fun startGame() {
        val event = OnGameStartedEvent()
        this.pipeline.pushEvent(event)
    }

    /**
     * Event.
     */

    class OnGameStartedEvent : BEvent()

    class OnGameFinishedEvent : BEvent()

    class OnWinEvent : BEvent()

    class OnLoseEvent : BEvent()
}