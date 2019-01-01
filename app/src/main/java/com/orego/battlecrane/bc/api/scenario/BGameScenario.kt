package com.orego.battlecrane.bc.api.scenario

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.api.model.player.BPlayer

/**
 * Initializes a game step by step.
 */

interface BGameScenario {

    /**
     * 1.) Fill player map.
     */

    fun getPlayers(context: BGameContext): List<BPlayer>

    /**
     * 2.) Invokes when player table is ready.
     */

    fun getUnits(context: BGameContext): List<BUnit>

    /**
     * 3.) Invokes when player table is ready.
     */

    val firstTurnPlayerPosition: Int
}