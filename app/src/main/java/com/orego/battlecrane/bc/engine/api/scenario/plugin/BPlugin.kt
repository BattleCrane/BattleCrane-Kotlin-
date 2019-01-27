package com.orego.battlecrane.bc.engine.api.scenario.plugin

import com.orego.battlecrane.bc.engine.api.context.BGameContext

interface BPlugin {

    fun install(context: BGameContext)
}