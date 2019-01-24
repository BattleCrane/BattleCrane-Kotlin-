package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.factory.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.vehicle.BSkirmishHumanTankOnCreateTrigger
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.implementation.BEmptyGrassField
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BSkirmishHumanFactoryOnProduceActionTrigger private constructor(
    context: BGameContext,
    var factory: BHumanFactory
) : BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val producableId = this.factory.producableId
        if (event is Event
            && producableId == event.producableId
            && this.factory.isProduceEnable
            && event.isEnable(this.context, this.factory)
        ) {
            event.perform(this.context, this.factory)
            this.pushToInnerPipes(event)
            this.pipeline.pushEvent(BOnProduceEnablePipe.Event(producableId, false))
            return event
        }
        return null
    }

    override fun isFinished() = !this.unitMap.containsKey(this.factory.unitId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        var factory = this@BSkirmishHumanFactoryOnProduceActionTrigger.factory

        override fun isFinished() = this@BSkirmishHumanFactoryOnProduceActionTrigger.isFinished()
    }

    /**
     * Event.
     */

    class Event(producableId: Long, val x: Int, val y: Int) :
        BOnProduceActionPipe.Event(producableId) {

        fun perform(context: BGameContext, factory: BHumanFactory) {
            context.pipeline.pushEvent(BSkirmishHumanTankOnCreateTrigger.Event(factory.playerId, this.x, this.y))
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
    }

    companion object {

        fun connect(context: BGameContext, factory: BHumanFactory) {
            BOnProduceActionNode.connect(context) {
                BSkirmishHumanFactoryOnProduceActionTrigger(
                    context,
                    factory
                )
            }
        }
    }
}