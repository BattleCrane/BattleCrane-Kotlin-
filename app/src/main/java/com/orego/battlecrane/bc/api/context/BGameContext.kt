package com.orego.battlecrane.bc.api.context

import com.orego.battlecrane.bc.api.context.mapManager.BMapManager
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.scenario.BGameScenario

class BGameContext(scenario: BGameScenario) {

    /**
     * Pipeline.
     */

    val pipeline = BPipeline()

    /**
     * Player manager.
     */

    val playerManager = BPlayerManager(scenario, this)

    /**
     * Secondly -- map manager.
     */

    val mapManager = BMapManager(scenario, this)
}