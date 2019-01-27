package com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.location

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.scenario.plugin.BUiPlugin
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

abstract class BUiLocationPlugin : BUiPlugin {

    override fun install(uiGameContext: BUiGameContext) {
        val uiUnitFactory = uiGameContext.uiUnitFactory
        for (uiUnitBuilderEntry in this.uiUnitBuilderMap) {
            uiUnitFactory.addBuilder(uiUnitBuilderEntry)
        }
    }

    protected abstract val uiUnitBuilderMap: Map<Class<out BUnit>, BUiUnit.Builder>
}