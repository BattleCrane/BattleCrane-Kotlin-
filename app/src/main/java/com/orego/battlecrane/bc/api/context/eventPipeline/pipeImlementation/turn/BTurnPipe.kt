package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.turn.node.BTurnNode

class BTurnPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "TURN_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BTurnNode(context))
    }

    open class TurnEvent(val ownerId: Long) : BEvent()
}