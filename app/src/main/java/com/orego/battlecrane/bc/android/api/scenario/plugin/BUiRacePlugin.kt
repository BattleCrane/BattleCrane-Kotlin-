package com.orego.battlecrane.bc.android.api.scenario.plugin

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

abstract class BUiRacePlugin {

    fun install(uiGameContext: BUiGameContext) {
        val uiUnitFactory = uiGameContext.uiUnitFactory
        for (uiUnitBuilderEntry in this.uiUnitBuilderMap) {
            uiUnitFactory.addBuilder(uiUnitBuilderEntry)
        }
        uiGameContext.uiAdjutantFactory.addBuilder(uiAdjutantBuilderPair.first, uiAdjutantBuilderPair.second)
    }

    protected abstract val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder>

    protected abstract val uiAdjutantBuilderPair: Pair<Class<out BAdjutant>, BAdjutantHolder.Builder>
}