package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode

class BOnDestroyUnitNode(context: BGameContext) : BNode(context) {

    override val name = NAME

    override fun handle(event: BEvent) =
        if (event is BOnDestroyUnitPipe.Event) {
            this.pushToInnerPipes(event)
        } else {
            null
        }

    companion object {

        const val NAME = "ON_DESTROY_UNIT_NODE"

        fun connect(context: BGameContext, create: () -> BNode) {
            context.pipeline.bindPipeToNode(NAME, create().intoPipe())
        }
    }
}