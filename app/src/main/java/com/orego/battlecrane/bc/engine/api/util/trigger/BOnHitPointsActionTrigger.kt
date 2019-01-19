package com.orego.battlecrane.bc.engine.api.util.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BHitPointableHeap
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

abstract class BOnHitPointsActionTrigger protected constructor(context: BGameContext, val hitPointable: BHitPointable) :
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
                    this.pipeline.pushEvent(
                        BOnDestroyUnitPipe.createEvent(this.hitPointable.unitId)
                    )
                    this.pipeline.pushEvent(
                        this.getInsteadDestroyedUnitEvent(this.hitPointable.x, this.hitPointable.y)
                    )
                }
            }
            return event
        }
        return null
    }

    abstract fun getInsteadDestroyedUnitEvent(x: Int, y: Int): BOnCreateUnitPipe.Event

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.hitPointableMap.containsKey(this.hitPointable.hitPointableId)

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val hitPointable = this@BOnHitPointsActionTrigger.hitPointable

        override fun isUnused() = this@BOnHitPointsActionTrigger.isUnused()
    }
}