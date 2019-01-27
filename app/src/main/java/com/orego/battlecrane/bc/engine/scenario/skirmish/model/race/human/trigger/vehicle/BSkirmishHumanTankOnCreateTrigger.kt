package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.vehicle

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.vehicle.builder.BSkirmishHumanTankBuilder
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BSkirmishHumanTankOnCreateTrigger private constructor(context: BGameContext, playerId: Long) :
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
     * Event.
     */

    class Event (playerId: Long, x: Int, y: Int) : BOnCreateUnitTrigger.Event(playerId, x, y) {

        override val width = BHumanTank.WIDTH

        override val height = BHumanTank.HEIGHT

        override fun createUnit(context: BGameContext) =
            BSkirmishHumanTankBuilder().build(context, this.playerId, this.x, this.y)
    }

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            BOnCreateUnitTrigger.connect(context) {
                BSkirmishHumanTankOnCreateTrigger(
                    context,
                    playerId
                )
            }
        }
    }
}