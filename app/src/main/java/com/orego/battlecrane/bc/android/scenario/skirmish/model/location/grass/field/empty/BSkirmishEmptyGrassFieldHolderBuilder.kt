package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.empty

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.holder.unit.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.empty.BEmptyGrassFieldHolder

class BSkirmishEmptyGrassFieldHolderBuilder : BEmptyGrassFieldHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUnitHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        return holder
    }
}