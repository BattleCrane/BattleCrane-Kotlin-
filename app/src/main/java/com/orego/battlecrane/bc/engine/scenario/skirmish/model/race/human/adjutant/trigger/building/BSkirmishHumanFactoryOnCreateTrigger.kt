package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.building

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.factory.builder.BSkirmishHumanFactoryBuilder
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

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

    class Event (playerId: Long, x: Int, y: Int) : BOnCreateUnitTrigger.Event(playerId, x, y) {

        override val width = BHumanFactory.WIDTH

        override val height = BHumanFactory.HEIGHT

        override fun createUnit(context: BGameContext) =
            BSkirmishHumanFactoryBuilder().build(context, this.playerId, this.x, this.y)
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