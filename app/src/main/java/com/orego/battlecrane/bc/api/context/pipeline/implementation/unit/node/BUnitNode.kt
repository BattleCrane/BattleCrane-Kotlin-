package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BUnitNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "UNIT_NODE"
    }

    override val name = NAME

    init {
        this.connectInnerPipe(BOnCreateUnitPipe(context))
        this.connectInnerPipe(BOnDestroyUnitPipe(context))
    }

    override fun handle(event: BEvent) =
        if (event is BUnitPipe.Event) {
            this.pushEventIntoPipes(event)
        } else {
            null
        }
}