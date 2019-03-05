package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.location.BUiLocationPlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.destroyed.BUiSkirmishDestroyedGrassFieldBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.empty.BUiSkirmishEmptyGrassFieldBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BUiSkirmishDestroyedGrassFieldOnCreateTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BUiSkirmishEmptyGrassFieldOnCreateTrigger
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BDestroyedGrassField
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField

class BUiSkirmishGrassLocationPlugin : BUiLocationPlugin() {

    override val renderFunctionMap: Map<Class<out BUnit>, (BUiGameContext, BUnit) -> BUiUnit> = mapOf(
        BEmptyGrassField::class.java to { context, unit ->
            BUiSkirmishEmptyGrassFieldBuilder(unit as BEmptyGrassField).build(context)
        },
        BDestroyedGrassField::class.java to { context, unit ->
            BUiSkirmishDestroyedGrassFieldBuilder(unit as BDestroyedGrassField).build(context)
        }
    )

    override fun install(uiGameContext: BUiGameContext) {
        super.install(uiGameContext)
        BUiSkirmishDestroyedGrassFieldOnCreateTrigger.connect(uiGameContext)
        BUiSkirmishEmptyGrassFieldOnCreateTrigger.connect(uiGameContext)
    }
}