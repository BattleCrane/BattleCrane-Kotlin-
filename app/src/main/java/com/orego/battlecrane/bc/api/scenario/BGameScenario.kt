package com.orego.battlecrane.bc.api.scenario

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.BMapManager
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer

interface BGameScenario {

    fun getStartPlayer(players: List<BPlayer>): BPlayer

    fun initMap(mapHolder: BMapManager.BMapHolder, context: BGameContext)

    fun initPlayerList(context: BGameContext): List<BPlayer>
}