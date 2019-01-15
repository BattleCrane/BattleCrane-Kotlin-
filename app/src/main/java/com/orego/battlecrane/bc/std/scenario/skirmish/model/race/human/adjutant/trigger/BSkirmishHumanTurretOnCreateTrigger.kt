package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.adjutant.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.model.adjutant.trigger.BUnitOnCreateTrigger
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit

@BAdjutantComponent
class BSkirmishHumanTurretOnCreateTrigger private constructor(context: BGameContext, playerId: Long) :
    BUnitOnCreateTrigger(context, playerId) {

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            BUnitOnCreateTrigger.connect(context) {
                BSkirmishHumanTurretOnCreateTrigger(
                    context,
                    playerId
                )
            }
        }
    }

    /**
     * Context.
     */

    override fun handle(event: BEvent): BEvent? {
        if (event is Event){
            return super.pushToInnerPipes(event)
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BUnitOnCreateTrigger.Pipe()

    /**
     * Event.
     */

    class Event private constructor(playerId: Long, x: Int, y: Int) : BUnitOnCreateTrigger.Event(playerId, x, y) {

        override fun create(context: BGameContext): BUnit {
            return BSkirmishHumanTurretBuilder().build(this.context)
        }

        override fun perform(context: BGameContext): Boolean {
            val controller = context.mapController
            val turret = this.create(context)
            val pipeline = context.pipeline
            val unitId = controller.getUnitIdByPosition(this.x, this.y)
            controller.placeUnitOnMap(turret)
            context.storage.addObject(turret)
            pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(unitId))
            pipeline.pushEvent(BOnAttackActionPipe.createEvent(turret.attackableId))
            return true
        }

        companion object {

            fun create(playerId: Long, x :Int, y : Int) = Event(playerId, x, y)
        }
    }
}