package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BDestroyedGrassField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.scenario.plugin.BLocationPlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.destroyed.BSkirmishDestroyedGrassFieldHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.empty.BSkirmishEmptyGrassFieldHolderBuilder

class BSkirmishGrassLocationPlugin : BLocationPlugin() {

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        BEmptyGrassField::class.java to BSkirmishEmptyGrassFieldHolderBuilder(),
        BDestroyedGrassField::class.java to BSkirmishDestroyedGrassFieldHolderBuilder()
    )
}