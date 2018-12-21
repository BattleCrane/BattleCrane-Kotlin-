package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline

class BActionPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "NAME"
    }
    override val name = NAME

    override val nodes: MutableList<Node>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}