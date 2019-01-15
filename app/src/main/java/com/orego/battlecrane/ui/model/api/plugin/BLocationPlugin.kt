package com.orego.battlecrane.ui.model.api.plugin

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

abstract class BLocationPlugin {

    abstract val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder>
}