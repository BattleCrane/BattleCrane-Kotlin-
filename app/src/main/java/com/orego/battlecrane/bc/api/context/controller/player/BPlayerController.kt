package com.orego.battlecrane.bc.api.context.controller.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.util.mapWithFilter

@BContextComponent
class BPlayerController(scenario: BGameScenario, context: BGameContext) {

    val ablePlayers: MutableList<Long>

    var currentPlayerId: Long

    var currentPlayerPosition: Int

    init {
        val players = scenario.getPlayers(context)
        val firstTurnPlayerPosition = scenario.getFirstTurnPlayerPosition()
        //Init fields:
        this.currentPlayerPosition = firstTurnPlayerPosition
        this.currentPlayerId = players[firstTurnPlayerPosition].playerId
        this.ablePlayers = players
            .mapWithFilter({ it.isAblePlayer }, { it.playerId })
            .toMutableList()
    }
}