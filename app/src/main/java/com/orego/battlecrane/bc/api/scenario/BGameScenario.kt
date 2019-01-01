package com.orego.battlecrane.bc.api.scenario

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.api.model.player.BPlayer

interface BGameScenario {

    fun getUnits(context: BGameContext): List<BUnit>

    fun getFirstTurnPlayerPosition(): Int

    fun getPlayers(context: BGameContext): List<BPlayer>
}