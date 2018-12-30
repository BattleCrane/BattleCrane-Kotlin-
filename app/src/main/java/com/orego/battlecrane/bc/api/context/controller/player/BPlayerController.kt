package com.orego.battlecrane.bc.api.context.controller.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.scenario.BGameScenario

@BContextComponent
class BPlayerController(scenario: BGameScenario, context: BGameContext) {

    val ablePlayers: MutableList<Long>

    var currentPlayerId: Long

    var playerPointer: Int

    init {
        val players = scenario.initPlayerList(context)
        val startPlayerPosition = scenario.getStartPlayerPosition()
        //Init fields:
        this.players = players
        this.ablePlayers = players.map { it.playerId }.toMutableList()
        this.currentPlayerId = players[startPlayerPosition].playerId
        this.playerPointer = startPlayerPosition
    }

    fun getPlayerById(id: Long) = this.players.find { it.playerId == id }!!
}