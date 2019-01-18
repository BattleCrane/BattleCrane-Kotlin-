package com.orego.battlecrane.bc.scenario.skirmish.model.race.human.adjutant.trigger.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.factory.builder.BSkirmishHumanFactoryBuilder

class BSkirmishHumanFactoryOnCreateTrigger private constructor(context: BGameContext, playerId: Long) :
    BOnCreateUnitTrigger(context, playerId) {

    /**
     * Context.
     */

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

    class Event private constructor(playerId: Long, x: Int, y: Int) : BOnCreateUnitTrigger.Event(playerId, x, y) {

        override fun create(context: BGameContext) =
            BSkirmishHumanFactoryBuilder().build(context, this.playerId, this.x, this.y)

        companion object {

            fun create(playerId: Long, x: Int, y: Int) =
                Event(
                    playerId,
                    x,
                    y
                )
        }
    }

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            BOnCreateUnitTrigger.connect(context) {
                BSkirmishHumanFactoryOnCreateTrigger(
                    context,
                    playerId
                )
            }
        }
    }
}