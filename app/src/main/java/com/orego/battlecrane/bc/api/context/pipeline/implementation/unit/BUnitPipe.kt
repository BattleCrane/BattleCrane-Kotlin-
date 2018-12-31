package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.BUnitNode

class BUnitPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "UNIT_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BUnitNode(context))

    open class UnitEvent : BEvent()
}