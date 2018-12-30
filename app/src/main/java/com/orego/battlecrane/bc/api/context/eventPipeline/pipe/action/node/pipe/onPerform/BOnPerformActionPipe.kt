package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.node.BOnPerformActionNode
import com.orego.battlecrane.bc.api.model.action.BAction

class BOnPerformActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_PERFORM_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnPerformActionNode(context))
    }

    open class OnActionPerformedNode(val action : BAction) : BActionPipe.ActionEvent()
}