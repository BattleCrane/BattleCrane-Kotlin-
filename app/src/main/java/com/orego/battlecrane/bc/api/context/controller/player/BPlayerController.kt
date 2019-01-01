package com.orego.battlecrane.bc.api.context.controller.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.util.mapWithFilter

@BContextComponent
class BPlayerController(context: BGameContext, var currentPlayerPosition: Int) {

    val ablePlayers: MutableList<Long>

    var currentPlayerId: Long

    init {
        val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)
        val players = playerHeap.getObjectList()
        //Init fields:
        this.currentPlayerId = players[this.currentPlayerPosition].playerId
        this.ablePlayers = players
            .mapWithFilter({ it.isAblePlayer(context) }, { it.playerId })
            .toMutableList()
    }
}