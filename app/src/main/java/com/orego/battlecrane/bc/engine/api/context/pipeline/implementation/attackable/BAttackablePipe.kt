package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.BAttackableNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

/**
 * Passes all attack events.
 */

class BAttackablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ATTACKABLE_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BAttackableNode(context))
    }

    /**
     * Attack event.
     */

    open class Event : BEvent()
}