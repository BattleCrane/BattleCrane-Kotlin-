package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BActionNode
import com.orego.battlecrane.bc.api.model.action.BAction

class BActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ACTION_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BActionNode(context))

    /**
     * ActionBundle.
     */

    open class ActionBundle(val action: BAction)
}