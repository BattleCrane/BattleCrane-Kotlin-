package com.orego.battlecrane.bc.std.scenario.skirmish.model.location.grass.field.empty

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField

class BSkirmishEmptyGrassFieldBuilder : BEmptyGrassField.Builder() {

    override fun build(context: BGameContext, playerid: Long, x: Int, y: Int): BEmptyGrassField {
        val emptyField = super.build(context, playerid, x, y)
        BOnDestroyUnitTrigger.connect(context, emptyField)
        return emptyField
    }
}