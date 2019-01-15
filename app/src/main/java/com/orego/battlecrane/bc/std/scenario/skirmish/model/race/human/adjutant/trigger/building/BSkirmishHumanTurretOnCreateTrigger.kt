package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.adjutant.trigger.building

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanTurret
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.turret.builder.BSkirmishHumanTurretBuilder
import com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.turret.trigger.BSkirmishHumanTurretOnAttackActionTrigger

class BSkirmishHumanTurretOnCreateTrigger private constructor(context: BGameContext, playerId: Long) :
    BOnCreateUnitTrigger(context, playerId) {

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            BOnCreateUnitTrigger.connect(context) {
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
        if (event is Event) {
            return super.pushToInnerPipes(event)
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

        override fun create(context: BGameContext): BHumanTurret {
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
            pipeline.pushEvent(BSkirmishHumanTurretOnAttackActionTrigger.Event.create(turret.attackableId))
            return true
        }

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