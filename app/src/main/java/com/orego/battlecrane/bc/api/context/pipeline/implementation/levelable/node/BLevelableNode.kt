package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent

@BContextComponent
class BLevelableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "LEVELABLE_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent) : BEvent? {
        return if (event is BLevelablePipe.LevelableEvent) {
            this.pushEventIntoPipes(event)
            event
        } else {
            null
        }
    }
}