package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.scenario.plugin.BUiLocationPlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.destroyed.BSkirmishDestroyedGrassFieldHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.empty.BSkirmishEmptyGrassFieldHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BSkirmishDestroyedGrassFieldHolderOnCreateTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BSkirmishEmptyGrassFieldHolderOnCreateTrigger
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BDestroyedGrassField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField

class BUiSkirmishGrassUiLocationPlugin : BUiLocationPlugin() {

    override fun install(uiGameContext: BUiGameContext) {
        super.install(uiGameContext)
        BSkirmishDestroyedGrassFieldHolderOnCreateTrigger.connect(uiGameContext)
        BSkirmishEmptyGrassFieldHolderOnCreateTrigger.connect(uiGameContext)
    }

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        BEmptyGrassField::class.java to BSkirmishEmptyGrassFieldHolderBuilder(),
        BDestroyedGrassField::class.java to BSkirmishDestroyedGrassFieldHolderBuilder()
    )
}