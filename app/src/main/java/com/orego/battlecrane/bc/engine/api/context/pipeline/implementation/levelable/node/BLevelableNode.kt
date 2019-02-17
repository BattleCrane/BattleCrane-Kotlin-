package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

/**
 * Checks all level event traffic.
 */

class BLevelableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "LEVELABLE_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnLevelActionPipe(context))
    }

    override fun handle(event: BEvent) : BEvent? {
        return if (event is BLevelablePipe.Event) {
            this.pushToInnerPipes(event)
        } else {
            null
        }
    }
}