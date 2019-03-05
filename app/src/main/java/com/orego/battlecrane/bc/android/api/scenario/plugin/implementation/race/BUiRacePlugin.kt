package com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.race

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.scenario.plugin.BUiPlugin
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

abstract class BUiRacePlugin(protected val playerId: Long) : BUiPlugin {

    protected abstract val renderFunctionMap: Map<Class<out BUnit>, (BUiGameContext, BUnit) -> BUiUnit>

    override fun install(uiGameContext: BUiGameContext) {
        uiGameContext.uiUnitFactory.renderFunctionMap.putAll(this.renderFunctionMap)
    }
}