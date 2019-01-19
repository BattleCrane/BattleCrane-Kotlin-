package com.orego.battlecrane.bc.android.api.scenario.plugin

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder

abstract class BLocationPlugin {

    abstract val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder>
}