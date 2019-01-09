package com.orego.battlecrane.ui.model.api.util

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.model.api.holder.adjutant.BAdjutantHolder
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

abstract class RaceFactory {

    abstract val uiUnitBuilders: Map<Class<out BUnit>, BUnitHolder.Builder>

    abstract val uiAdjutantBuilder: Pair<Class<out BAdjutant>, BAdjutantHolder.Builder>
}