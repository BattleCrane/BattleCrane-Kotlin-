package com.orego.battlecrane.bc.api.context.pipeline.implementation.turn

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent

@BContextComponent
class BTurnPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "TURN_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BTurnNode(context))
    }

    open class TurnEvent(val playerId: Long) : BEvent()
}