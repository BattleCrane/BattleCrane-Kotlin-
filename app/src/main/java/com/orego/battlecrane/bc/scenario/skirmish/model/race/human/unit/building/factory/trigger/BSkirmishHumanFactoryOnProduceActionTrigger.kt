package com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.factory.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.std.location.grass.field.implementation.empty.BEmptyGrassField
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.scenario.skirmish.model.race.human.adjutant.trigger.vehicle.BSkirmishHumanTankOnCreateTrigger

class BSkirmishHumanFactoryOnProduceActionTrigger private constructor(
    context: BGameContext,
    var factory: BHumanFactory
) : BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        val producableId = this.factory.producableId
        if (event is Event
            && producableId == event.producableId
            && this.factory.isProduceEnable
            && event.isEnable(this.context, this.factory)
        ) {
            event.perform(this.context, this.factory)
            this.pushToInnerPipes(event)
            this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
            return event
        }
        return null
    }

    /**
     * Event.
     */

    class Event private constructor(producableId: Long, val x: Int, val y: Int) :
        BOnProduceActionPipe.Event(producableId) {

        fun perform(context: BGameContext, factory: BHumanFactory) {
            context.pipeline.pushEvent(BSkirmishHumanTankOnCreateTrigger.Event.create(factory.playerId, this.x, this.y))
        }

        fun isEnable(context: BGameContext, factory: BHumanFactory): Boolean {
            val controller = context.mapController
            val otherUnit = controller.getUnitByPosition(context, this.x, this.y)
            if (otherUnit !is BEmptyGrassField) {
                return false
            }
            val level = factory.currentLevel
            val playerId = factory.playerId
            val otherPlayerId = otherUnit.playerId
            if (level == BHumanFactory.FIRST_LEVEL && otherPlayerId == playerId) {
                return true
            }
            val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)
            val player = playerHeap[playerId]
            if (level == BHumanFactory.SECOND_LEVEL && !player.isEnemy(otherPlayerId)) {
                return true
            }
            if (level == BHumanFactory.THIRD_LEVEL) {
                return true
            }
            return false
        }

        companion object {

            fun create(factoryUnitId: Long, x: Int, y: Int) = Event(factoryUnitId, x, y)
        }
    }

    companion object {

        fun connect(context: BGameContext, factory: BHumanFactory) {
            BOnLevelActionNode.connect(context) {
                BSkirmishHumanFactoryOnProduceActionTrigger(
                    context,
                    factory
                )
            }
        }
    }
}