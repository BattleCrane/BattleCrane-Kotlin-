package com.orego.battlecrane.ui.model.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.implementation.destroyed.BDestroyedGrassField
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.api.scenario.plugin.BLocationPlugin
import com.orego.battlecrane.ui.model.scenario.skirmish.model.location.grass.field.destroyed.BSkirmishDestroyedGrassFieldHolderBuilder
import com.orego.battlecrane.ui.model.scenario.skirmish.model.location.grass.field.empty.BSkirmishEmptyGrassFieldHolderBuilder

class BSkirmishGrassLocationPlugin : BLocationPlugin() {

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        BEmptyGrassField::class.java to BSkirmishEmptyGrassFieldHolderBuilder(),
        BDestroyedGrassField::class.java to BSkirmishDestroyedGrassFieldHolderBuilder()
    )
}