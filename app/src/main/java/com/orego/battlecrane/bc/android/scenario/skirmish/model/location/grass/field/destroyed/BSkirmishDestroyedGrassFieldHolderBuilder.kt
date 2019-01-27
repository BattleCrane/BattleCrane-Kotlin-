package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.destroyed

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.destroyed.BUiDestroyedGrassField
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishDestroyedGrassFieldHolderBuilder : BUiDestroyedGrassField.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiUnit {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        return holder
    }
}