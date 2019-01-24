package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.destroyed

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.util.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.destroyed.BDestroyedGrassFieldHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishDestroyedGrassFieldHolderBuilder : BDestroyedGrassFieldHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUnitHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        return holder
    }
}