package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

class BOnProduceEnableNode(context: BGameContext) : BNode(context) {

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.Event) {
            println("HANDLE ENABLE EVENT!")
            return this.pushToInnerPipes(event)
        }
        return null
    }

    companion object {

        const val NAME = "ON_PRODUCE_ENABLE_NODE"

        fun connect(context: BGameContext, create: () -> BNode) {
            context.pipeline.bindPipeToNode(NAME, create().intoPipe())
        }
    }
}