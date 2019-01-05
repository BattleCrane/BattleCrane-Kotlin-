package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

@BContextComponent
class BOnCreateUnitNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_CREATE_UNIT_NODE"
    }

    override val name =
        NAME

    /**
     * Creates new unit.
     */

    override fun handle(event: BEvent) =
        if (event is BOnCreateUnitPipe.Event) {
            this.pushEventIntoPipes(event)
        } else {
            null
        }
}