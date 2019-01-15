package com.orego.battlecrane.bc.api.model.property.hitPointable.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BHitPointableHeap
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable
import com.orego.battlecrane.bc.api.model.unit.BUnit

class BOnHitPointsActionTrigger private constructor(context: BGameContext, val hitPointable: BHitPointable) :
    BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val hitPointableMap = context.storage.getHeap(BHitPointableHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnHitPointsActionPipe.Event
            && event.hitPointableId == this.hitPointable.hitPointableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            this.pushToInnerPipes(event)
            if (this.hitPointable.currentHitPoints <= 0) {
                if (this.hitPointable is BUnit) {
                    this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.hitPointable.unitId))
                }
            }
            return event
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.hitPointableMap.containsKey(this.hitPointable.hitPointableId)

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BOnHitPointsActionTrigger.hitPointable

        override fun isUnused() = this@BOnHitPointsActionTrigger.isUnused()
    }

    companion object {

        fun connect(context: BGameContext, hitPointable: BHitPointable) {
            BOnHitPointsActionNode.connect(context) {
                BOnHitPointsActionTrigger(
                    context,
                    hitPointable
                )
            }
        }
    }
}