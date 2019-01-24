package com.orego.battlecrane.bc.engine.api.util.trigger.hitPointable

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BHitPointableHeap
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable

abstract class BOnHitPointsActionTrigger protected constructor(context: BGameContext, open val hitPointable: BHitPointable) :
    BNode(context) {

    /**
     * Context.
     */

    protected val hitPointableMap = context.storage.getHeap(BHitPointableHeap::class.java).objectMap

    protected abstract fun onHitPointsLost()

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnHitPointsActionPipe.Event
            && event.hitPointableId == this.hitPointable.hitPointableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            this.pushToInnerPipes(event)
            if (this.hitPointable.currentHitPoints <= 0) {
                this.onHitPointsLost()
            }
            return event
        }
        return null
    }

    override fun isFinished() = !this.hitPointableMap.containsKey(this.hitPointable.hitPointableId)
}