package com.orego.battlecrane.bc.api.context

import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.context.controller.player.BPlayerController
import com.orego.battlecrane.bc.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.storage.BStorage
import com.orego.battlecrane.bc.api.scenario.BGameScenario

/**
 * Game.
 */

class BGameContext {

    /**
     * Id Generator.
     */

    val contextGenerator = BContextGenerator()


    /**
     * Pipeline.
     */

    val pipeline = BPipeline(this)

    /**
     * Storage.
     */

    val storage = BStorage(this)

    /**
     * Controller.
     */

    val mapController = BMapController()

    val playerController = BPlayerController(this)

    /**
     * Launches a game.
     */

    fun setScenario(scenario: BGameScenario) {
        this.storage.setScenario(scenario)
        this.playerController.setScenario(scenario)
        this.mapController.initMap(this)
    }

    fun startGame() {
        this.pipeline.pushEvent(OnGameStartedEvent())
    }

    /**
     * Event.
     */

    class OnGameStartedEvent : BEvent()

    class OnGameFinishedEvent : BEvent()

    class OnWinEvent : BEvent()

    class OnLoseEvent : BEvent()
}