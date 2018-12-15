package com.orego.battlecrane.bc.api.scenario

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.BMapManager
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer

interface BGameScenario {

    fun getStartPlayer(players: List<BPlayer>): BPlayer

    fun initMap(mapHolder: BMapManager.BMapHolder, context: BGameContext)

    fun initPlayerList(context: BGameContext): List<BPlayer>
}