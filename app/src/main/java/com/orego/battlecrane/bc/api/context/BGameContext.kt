package com.orego.battlecrane.bc.api.context

import com.orego.battlecrane.bc.api.context.mapManager.BMapManager
import com.orego.battlecrane.bc.api.context.pipeline.PipeLine
import com.orego.battlecrane.bc.api.context.playerManager.BPlayerManager
import com.orego.battlecrane.bc.api.scenario.BGameScenario

class BGameContext(scenario: BGameScenario) {

    /**
     * Firstly createAction player manager.
     */

    val playerManager = BPlayerManager(scenario, this)

    /**
     * Secondly -- map manager.
     */

    val mapManager = BMapManager(scenario, this)

    /**
     * Pipeline.
     */

    val pipeline = PipeLine()
}