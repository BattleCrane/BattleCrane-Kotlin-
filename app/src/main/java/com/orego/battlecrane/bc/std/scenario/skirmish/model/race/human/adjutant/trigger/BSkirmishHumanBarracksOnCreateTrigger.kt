package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.adjutant.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.model.adjutant.trigger.BUnitOnCreateTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.BSkirmishHumanBarracksBuilder

@BAdjutantComponent
class BSkirmishHumanBarracksOnCreateTrigger private constructor(context: BGameContext, playerId: Long) :
    BUnitOnCreateTrigger(context, playerId) {

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

    inner class Pipe : BUnitOnCreateTrigger.Pipe()

    /**
     * Static.
     */

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            BUnitOnCreateTrigger.connect(context) {
                BSkirmishHumanBarracksOnCreateTrigger(context, playerId)
            }
        }
    }


    /**
     * Event.
     */

    class Event private constructor(playerId: Long, x: Int, y: Int) : BUnitOnCreateTrigger.Event(playerId, x, y) {

        override fun createUnit(context: BGameContext) =
            BSkirmishHumanBarracksBuilder().build(
                context,
                this.playerId,
                this.x,
                this.y
            )

        companion object {

            fun create(playerId: Long, x: Int, y: Int) =
                Event(playerId, x, y)
        }
    }
}