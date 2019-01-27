package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.field.empty

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.util.trigger.unit.BUiOnDestroyUnitTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger.BUiSkirmishEmptyGrassFieldOnOwnerChangedTrigger
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.empty.BUiEmptyGrassField
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BUiSkirmishEmptyGrassFieldBuilder : BUiEmptyGrassField.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BUiEmptyGrassField {
        val holder = super.build(uiGameContext, item)
        BUiOnDestroyUnitTrigger.connect(uiGameContext, holder)
        BUiSkirmishEmptyGrassFieldOnOwnerChangedTrigger.connect(uiGameContext, holder)
        return holder
    }
}