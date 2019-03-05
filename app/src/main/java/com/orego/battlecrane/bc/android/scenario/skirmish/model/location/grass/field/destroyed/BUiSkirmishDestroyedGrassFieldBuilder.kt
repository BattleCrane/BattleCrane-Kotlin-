package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.destroyed

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.standardImpl.location.grass.unit.field.destroyed.BUiDestroyedGrassField
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishDestroyedGrassFieldBuilder : BUiDestroyedGrassField.Builder() {

    override fun onCreate(uiGameContext: BUiGameContext, item: BUnit): BUiUnit {
        val holder = super.onCreate(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        return holder
    }
}