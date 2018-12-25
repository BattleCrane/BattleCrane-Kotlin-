package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.BUnitNode
import com.orego.battlecrane.bc.api.model.unit.BUnit

class BUnitPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "UNIT_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BUnitNode(context))

    open class UnitBundle(val unit : BUnit)
}