package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.field

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BDestroyedGrassField

class BSkirmishDestroyedGrassFieldBuilder : BDestroyedGrassField.Builder() {

    override fun build(context: BGameContext, playerid: Long, x: Int, y: Int): BDestroyedGrassField {
        val destroyedField = super.build(context, playerid, x, y)
        BOnDestroyUnitTrigger.connect(context, destroyedField)
        return destroyedField
    }
}