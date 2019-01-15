package com.orego.battlecrane.ui.model.api.scenario.plugin

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.model.api.holder.adjutant.BAdjutantHolder
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

abstract class BRacePlugin {

    abstract val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder>

    abstract val uiAdjutantBuilderPair: Pair<Class<out BAdjutant>, BAdjutantHolder.Builder>
}