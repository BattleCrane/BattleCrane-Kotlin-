package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.field

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnOwnerChangedUnitTrigger
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField

class BSkirmishEmptyGrassFieldBuilder : BEmptyGrassField.Builder() {

    override fun build(context: BGameContext, playerid: Long, x: Int, y: Int): BEmptyGrassField {
        val emptyField = super.build(context, playerid, x, y)
        BOnDestroyUnitTrigger.connect(context, emptyField)
        BOnOwnerChangedUnitTrigger.connect(context, emptyField)
        return emptyField
    }
}