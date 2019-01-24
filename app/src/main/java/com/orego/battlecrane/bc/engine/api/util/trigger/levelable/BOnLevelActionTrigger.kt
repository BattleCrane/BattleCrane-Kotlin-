package com.orego.battlecrane.bc.engine.api.util.trigger.levelable

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BLevelableHeap
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable

abstract class BOnLevelActionTrigger protected constructor(context: BGameContext, open val levelable: BLevelable) :
    BNode(context) {

    /**
     * Context.
     */

    protected val levelableMap = context.storage.getHeap(BLevelableHeap::class.java).objectMap

    protected abstract fun onLevelChanged()

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnLevelActionPipe.Event
            && this.levelable.levelableId == event.levelableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            this.pushToInnerPipes(event)
            this.onLevelChanged()
            return event
        }
        return null
    }

    override fun isFinished() = !this.levelableMap.containsKey(this.levelable.levelableId)
}