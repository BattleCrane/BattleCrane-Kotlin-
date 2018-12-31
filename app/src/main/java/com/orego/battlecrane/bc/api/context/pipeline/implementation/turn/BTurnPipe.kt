package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode

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