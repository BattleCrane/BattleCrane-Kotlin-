package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BOnLevelActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_LEVEL_CHANGED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnLevelActionPipe.Event) {
            return this.pushEventIntoPipes(event)
        }
        return null
    }
}