package com.orego.battlecrane.bc.engine.api.scenario.plugin.race

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer

typealias BRacePlugin = (context: BGameContext, player : BPlayer) -> Unit