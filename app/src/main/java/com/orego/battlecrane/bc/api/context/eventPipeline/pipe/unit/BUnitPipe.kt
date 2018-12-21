package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.BOnCreateUnitNode

class BUnitPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "NAME"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BOnCreateUnitNode(context))
}