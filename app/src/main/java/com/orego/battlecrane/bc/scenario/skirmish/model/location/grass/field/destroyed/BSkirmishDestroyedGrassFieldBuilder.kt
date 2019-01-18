package com.orego.battlecrane.bc.scenario.skirmish.model.location.grass.field.destroyed

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.std.location.grass.field.implementation.destroyed.BDestroyedGrassField

class BSkirmishDestroyedGrassFieldBuilder : BDestroyedGrassField.Builder() {

    override fun build(context: BGameContext, playerid: Long, x: Int, y: Int): BDestroyedGrassField {
        val destroyedField = super.build(context, playerid, x, y)
        BOnDestroyUnitTrigger.connect(context, destroyedField)
        return destroyedField
    }
}