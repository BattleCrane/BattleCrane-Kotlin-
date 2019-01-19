package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.building

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.model.unit.trigger.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.turret.builder.BSkirmishHumanTurretBuilder
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.turret.trigger.BSkirmishHumanTurretOnAttackActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

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

        override val width = BHumanTurret.WIDTH

        override val height = BHumanTurret.HEIGHT

        override fun create(context: BGameContext) =
            BSkirmishHumanTurretBuilder().build(context, this.playerId, this.x, this.y)

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