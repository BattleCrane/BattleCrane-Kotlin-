package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.field.BSkirmishEmptyGrassFieldBuilder
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

class BSkirmishEmptyGrassFieldOnCreateTrigger private constructor(context: BGameContext) :
    BOnCreateUnitTrigger(context, BPlayer.NEUTRAL_ID) {

    override fun handle(event: BEvent): BEvent? {
        if (event is Event) {
            return super.handle(event)
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BOnCreateUnitTrigger.Pipe()

    /**
     * Event.
     */

    class Event(playerId: Long, x: Int, y: Int) : BOnCreateUnitTrigger.Event(playerId, x, y) {

        override val width = BGrassField.WIDTH

        override val height = BGrassField.HEIGHT

        override fun createUnit(context: BGameContext) =
            BSkirmishEmptyGrassFieldBuilder(this.playerId, this.x, this.y).build(context)
    }

    /**
     * Static.
     */

    companion object {

        fun connect(context: BGameContext) {
            BOnCreateUnitTrigger.connect(context) {
                BSkirmishEmptyGrassFieldOnCreateTrigger(
                    context
                )
            }
        }
    }
}