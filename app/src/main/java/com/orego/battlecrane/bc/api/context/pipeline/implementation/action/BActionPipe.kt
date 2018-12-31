package com.orego.battlecrane.bc.api.context.pipeline.implementation.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.BActionNode

@BContextComponent
class BActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ACTION_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BActionNode(context))

    /**
     * ActionEvent.
     */

    open class ActionEvent : BEvent()
}