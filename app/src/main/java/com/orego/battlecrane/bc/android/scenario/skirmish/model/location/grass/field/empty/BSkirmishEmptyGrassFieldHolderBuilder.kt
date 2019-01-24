package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.empty

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BSkirmishEmptyGrassFieldHolderOnOwnerChangedTrigger
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.empty.BEmptyGrassFieldHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishEmptyGrassFieldHolderBuilder : BEmptyGrassFieldHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BEmptyGrassFieldHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishEmptyGrassFieldHolderOnOwnerChangedTrigger.connect(uiGameContext, holder)
        return holder
    }
}