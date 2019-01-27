package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.barracks.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.model.unit.type.BEmptyField
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.infantry.BSkirmishHumanMarineOnCreateTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BSkirmishHumanBarracksOnProduceActionTrigger private constructor(
    context: BGameContext,
    val barracks: BHumanBarracks
) : BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val producableId = this.barracks.producableId
        if (event is Event
            && producableId == event.producableId
            && this.barracks.isProduceEnable
            && event.isEnable(this.context, this.barracks)
        ) {
            event.perform(this.context, this.barracks)
            this.pushToInnerPipes(event)
            this.pipeline.pushEvent(BOnProduceEnablePipe.Event(producableId, false))
            return event
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.barracks.unitId)

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    /**
     * Event.
     */

    class Event(producableId: Long, val x: Int, val y: Int) :
        BOnProduceActionPipe.Event(producableId) {

        fun perform(context: BGameContext, barracks: BHumanBarracks) {
            context.pipeline.pushEvent(
                BSkirmishHumanMarineOnCreateTrigger.Event(
                    barracks.playerId,
                    this.x,
                    this.y
                )
            )
        }

        fun isEnable(context: BGameContext, barracks: BHumanBarracks): Boolean {
            val controller = context.mapController
            val otherUnit = controller.getUnitByPosition(context, this.x, this.y)
            if (otherUnit !is BEmptyField) {
                return false
            }
            val barracksLevel = barracks.currentLevel
            val barracksPlayerId = barracks.playerId
            val otherPlayerId = otherUnit.playerId
            if (barracksLevel == BHumanBarracks.FIRST_LEVEL && otherPlayerId == barracksPlayerId) {
                return true
            }
            val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)
            val barracksOwner = playerHeap[barracksPlayerId]
            if (barracksLevel == BHumanBarracks.SECOND_LEVEL && !barracksOwner.isEnemy(otherPlayerId)) {
                return true
            }
            if (barracksLevel == BHumanBarracks.THIRD_LEVEL) {
                return true
            }
            return false
        }
    }

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            BOnProduceActionNode.connect(context) {
                BSkirmishHumanBarracksOnProduceActionTrigger(
                    context,
                    barracks
                )
            }
        }
    }
}