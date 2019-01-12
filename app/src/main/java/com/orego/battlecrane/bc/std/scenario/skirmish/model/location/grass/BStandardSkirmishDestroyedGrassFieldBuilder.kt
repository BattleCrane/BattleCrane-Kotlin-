package com.orego.battlecrane.bc.std.scenario.skirmish.model.location.grass

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.entity.main.unit.trigger.BUnitOnDestroyTrigger
import com.orego.battlecrane.bc.std.location.grass.field.implementation.destroyed.BDestroyedGrassField

class BStandardSkirmishDestroyedGrassFieldBuilder : BDestroyedGrassField.Builder() {

    override fun build(context: BGameContext, playerid: Long, x: Int, y: Int): BDestroyedGrassField {
        val destroyedField = super.build(context, playerid, x, y)
        BUnitOnDestroyTrigger.connect(context, destroyedField)
        return destroyedField
    }
}