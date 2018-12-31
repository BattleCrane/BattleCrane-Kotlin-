package com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.BActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onCreate.BOnCreateActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onDestroy.BOnDestroyActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onPerform.BOnPerformActionPipe

@BContextComponent
class BActionNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ACTION_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnCreateActionPipe(context))
        this.connectInnerPipe(BOnPerformActionPipe(context))
        this.connectInnerPipe(BOnDestroyActionPipe(context))
    }

    override fun handle(event: BEvent): BEvent? {
        return if (event is BActionPipe.ActionEvent) {
            this.pushEventIntoPipes(event)
            event
        } else {
            null
        }
    }
}