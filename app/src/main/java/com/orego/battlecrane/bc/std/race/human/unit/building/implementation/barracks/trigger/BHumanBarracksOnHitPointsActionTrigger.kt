package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks

@BUnitComponent
class BHumanBarracksOnHitPointsActionTrigger(context: BGameContext, val barracks: BHumanBarracks) : BNode(context) {

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            val pipe = BHumanBarracksOnHitPointsActionTrigger(context, barracks).intoPipe()
            context.pipeline.bindPipeToNode(BOnHitPointsActionNode.NAME, pipe)
        }
    }

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnHitPointsActionPipe.Event
            && event.hitPointableId == this.barracks.hitPointableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            this.pushToInnerPipes(event)
            if (this.barracks.currentHitPoints <= 0) {
                this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.barracks.unitId))
            }
            return event
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.unitMap.containsKey(this.barracks.unitId)

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BHumanBarracksOnHitPointsActionTrigger.barracks

        override fun isUnused() = this@BHumanBarracksOnHitPointsActionTrigger.isUnused()
    }
}