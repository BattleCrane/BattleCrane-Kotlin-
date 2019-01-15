package com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BOnProduceEnableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_PRODUCE_ENABLE_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.Event) {
            return this.pushToInnerPipes(event)
        }
        return null
    }
}