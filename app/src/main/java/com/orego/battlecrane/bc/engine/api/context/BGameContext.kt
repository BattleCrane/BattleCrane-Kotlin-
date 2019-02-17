package com.orego.battlecrane.bc.engine.api.context

import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.context.controller.player.BPlayerController
import com.orego.battlecrane.bc.engine.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.engine.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.storage.BStorage

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

    val pipeline = BPipeline()

    /**
     * Storage.
     */

    val storage = BStorage()

    /**
     * Controller.
     */

    val mapController = BMapController(this)

    val playerController = BPlayerController()

    /**
     * Launches a game.
     */

    fun startGame() {
        this.pipeline.pushEvent(OnGameStartedEvent())
    }

    /**
     * Event.
     */

    class OnGameStartedEvent : BEvent()

    class OnGameFinishedEvent : BEvent()

    class OnWinEvent(val playerId: Long) : BEvent()

    class OnLoseEvent(val playerId: Long) : BEvent()
}