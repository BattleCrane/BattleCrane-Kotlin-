package com.orego.battlecrane.bc.api.scenario

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.BMapManager
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer

interface BGameScenario {

    fun getStartPlayerPosition(): Int

    fun initMap(mapManager: BMapManager, context: BGameContext)

    fun initPlayerList(context: BGameContext): List<BPlayer>
}