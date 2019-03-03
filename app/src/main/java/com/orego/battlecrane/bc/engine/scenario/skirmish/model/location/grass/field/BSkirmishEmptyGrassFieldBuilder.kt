package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.field

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnDestroyUnitTrigger
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnOwnerChangedUnitTrigger
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField

class BSkirmishEmptyGrassFieldBuilder(playerId: Long, x: Int, y: Int) : BEmptyGrassField.Builder(playerId, x, y) {

    override fun onCreate(context: BGameContext): BEmptyGrassField {
        val emptyField = super.onCreate(context)
        BOnDestroyUnitTrigger.connect(context, emptyField)
        BOnOwnerChangedUnitTrigger.connect(context, emptyField)
        return emptyField
    }
}