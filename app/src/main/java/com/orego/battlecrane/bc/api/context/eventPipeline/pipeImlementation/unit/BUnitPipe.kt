package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.unit.node.BUnitNode

class BUnitPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "UNIT_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BUnitNode(context))

    open class UnitEvent : BEvent()
}