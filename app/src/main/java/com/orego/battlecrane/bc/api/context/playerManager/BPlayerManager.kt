package com.orego.battlecrane.bc.api.context.playerManager

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.api.util.BIdGenerator
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.timer

class BPlayerManager(scenario: BGameScenario, context: BGameContext) {

    val players: List<BPlayer>

    val ablePlayers: MutableList<Long>

    var currentPlayerId: Long

    var playerPointer: Int

    init {
        val players = scenario.initPlayerList(context)
        val startPlayerPosition = scenario.getStartPlayerPosition()
        //Init fields:
        this.players = players
        this.ablePlayers = players.map { it.id }.toMutableList()
        this.currentPlayerId = players[startPlayerPosition].id
        this.playerPointer = startPlayerPosition
    }

    fun getPlayerById(id: Long) = this.players.find { it.id == id }!!
}