package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline

class BOnCreateUnitPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "UNIT_NODE"
    }

    override val name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val nodes: MutableList<Node>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}