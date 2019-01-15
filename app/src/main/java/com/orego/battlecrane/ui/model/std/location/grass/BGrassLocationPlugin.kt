package com.orego.battlecrane.ui.model.std.location.grass

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.implementation.destroyed.BDestroyedGrassField
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.api.plugin.BLocationPlugin
import com.orego.battlecrane.ui.model.std.location.grass.field.destroyed.BDestroyedFieldHolder
import com.orego.battlecrane.ui.model.std.location.grass.field.empty.BEmptyFieldHolder

class BGrassLocationPlugin : BLocationPlugin() {

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        BEmptyGrassField::class.java to BEmptyFieldHolder.Builder(),
        BDestroyedGrassField::class.java to BDestroyedFieldHolder.Builder()
    )
}