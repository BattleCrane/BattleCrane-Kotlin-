package com.orego.battlecrane.bc.std.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.unit.trigger.BUnitOnDestroyTrigger
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField

class BStandardSkirmishEmptyGrassFieldBuilder : BEmptyGrassField.Builder() {

    override fun build(context: BGameContext, playerid: Long, x: Int, y: Int): BEmptyGrassField {
        val emptyField = super.build(context, playerid, x, y)
        BUnitOnDestroyTrigger.connect(context, emptyField)
        return emptyField
    }
}