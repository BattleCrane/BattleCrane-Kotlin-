package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.BUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BUnitPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "UNIT_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BUnitNode(context))
    }

    open class Event : BEvent()
}