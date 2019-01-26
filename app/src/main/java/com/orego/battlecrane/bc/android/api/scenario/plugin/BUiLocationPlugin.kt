package com.orego.battlecrane.bc.android.api.scenario.plugin

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

abstract class BUiLocationPlugin {

    open fun install(uiGameContext: BUiGameContext) {
        val uiUnitFactory = uiGameContext.uiUnitFactory
        for (uiUnitBuilderEntry in this.uiUnitBuilderMap) {
            uiUnitFactory.addBuilder(uiUnitBuilderEntry)
        }
    }

    protected abstract val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder>
}