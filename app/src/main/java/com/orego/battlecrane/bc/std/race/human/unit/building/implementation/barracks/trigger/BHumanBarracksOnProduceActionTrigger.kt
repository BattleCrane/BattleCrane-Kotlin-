package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.unit.attribute.BEmptyField
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.marine.BHumanMarine

@BUnitComponent
class BHumanBarracksOnProduceActionTrigger private constructor(context: BGameContext, val barracks: BHumanBarracks) :
    BNode(context) {

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            val pipe = BHumanBarracksOnProduceActionTrigger(context, barracks).intoPipe()
            context.pipeline.bindPipeToNode(BOnProduceActionNode.NAME, pipe)
        }

        fun createEvent(barracksUnitId: Long, x: Int, y: Int) =
            Event(
                barracksUnitId,
                x,
                y
            )
    }

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
            this.pushEventIntoPipes(event)
            this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
            return event
        }
        return null
    }

    /**
     * Event.
     */

    class Event(producableId: Long, val x: Int, val y: Int) :
        BOnProduceActionPipe.Event(producableId) {

        fun perform(context: BGameContext, barracks: BHumanBarracks) {
            context.pipeline.pushEvent(BHumanMarine.OnCreateNode.createEvent(barracks.playerId, this.x, this.y))
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

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.unitMap.containsKey(this.barracks.unitId)

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BHumanBarracksOnProduceActionTrigger.barracks

        override fun isUnused() = this@BHumanBarracksOnProduceActionTrigger.isUnused()
    }
}