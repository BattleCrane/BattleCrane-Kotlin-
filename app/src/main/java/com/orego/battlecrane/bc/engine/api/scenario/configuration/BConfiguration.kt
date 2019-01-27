package com.orego.battlecrane.bc.engine.api.scenario.configuration

import com.orego.battlecrane.bc.engine.api.context.BGameContext

interface BConfiguration {

    fun install(context: BGameContext)
}