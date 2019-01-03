package com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BOnProduceActionNode(context: BGameContext) : BNode(context){

    companion object {

        const val NAME = "ON_PRODUCE_ACTION_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceActionPipe.Event) {
            return this.pushEventIntoPipes(event)
        }
        return null
    }
}