package com.orego.battlecrane.bc.api.context.controller.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.util.mapWithFilter

class BPlayerController(private val context: BGameContext) {

    companion object {

        private const val NOT_INITIALIZED = -1
    }

    var currentPlayerPosition: Int = NOT_INITIALIZED

    var currentPlayerId: Long = NOT_INITIALIZED.toLong()

    lateinit var ablePlayers: MutableList<Long>

    fun setScenario(scenario: BGameScenario) {
        val playerHeap = this.context.storage.getHeap(BPlayerHeap::class.java)
        val players = playerHeap.getObjectList()
        val startPosition = scenario.startTurnPlayerPosition
        //Init fields:
        this.currentPlayerPosition = startPosition
        this.currentPlayerId = players[startPosition].playerId
        this.ablePlayers = players
            .mapWithFilter({ it.isAblePlayer(context) }, { it.playerId })
            .toMutableList()
    }
}