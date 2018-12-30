package com.orego.battlecrane.bc.api.scenario

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.model.player.BPlayer

interface BGameScenario {

    fun getStartPlayerPosition(): Int

    fun initMap(mapController: BMapController, context: BGameContext)

    fun initPlayerList(context: BGameContext): List<BPlayer>
}