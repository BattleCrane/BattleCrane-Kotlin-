package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.adjutant.trigger.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.barracks.builder.BSkirmishHumanBarracksBuilder

class BSkirmishHumanBarracksOnCreateTrigger private constructor(context: BGameContext, playerId: Long) :
    BOnCreateUnitTrigger(context, playerId) {

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
     * Static.
     */

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            BOnCreateUnitTrigger.connect(context) {
                BSkirmishHumanBarracksOnCreateTrigger(
                    context,
                    playerId
                )
            }
        }
    }


    /**
     * Event.
     */

    class Event private constructor(playerId: Long, x: Int, y: Int) : BOnCreateUnitTrigger.Event(playerId, x, y) {

        override fun create(context: BGameContext) =
            BSkirmishHumanBarracksBuilder().build(
                context,
                this.playerId,
                this.x,
                this.y
            )

        companion object {

            fun create(playerId: Long, x: Int, y: Int) =
                Event(
                    playerId,
                    x,
                    y
                )
        }
    }
}