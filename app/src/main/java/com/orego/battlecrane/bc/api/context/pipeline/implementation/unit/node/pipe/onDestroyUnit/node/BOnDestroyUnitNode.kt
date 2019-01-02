package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BOnDestroyUnitNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_DESTROY_UNIT_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        return if (event is BOnDestroyUnitPipe.Event) {
            event.also { pushEventIntoPipes(it) }
        } else {
            null
        }
    }
}