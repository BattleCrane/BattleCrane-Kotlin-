package com.orego.battlecrane.bc.api.scenario

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.api.model.player.BPlayer

/**
 * Initializes a game step by step.
 */

interface BGameScenario {

    /**
     * 1.) Fill player table.
     */

    fun getPlayers(context: BGameContext): List<BPlayer>

    /**
     * 2.) Fill adjutant table.
     */

    fun getAdjutants(context: BGameContext) : List<BAdjutant>

    /**
     * 3.) Invokes when player table is ready.
     */

    fun getUnits(context: BGameContext): List<BUnit>

    /**
     * 4.) Invokes when player table is ready.
     */

    val startTurnPlayerPosition: Int
}