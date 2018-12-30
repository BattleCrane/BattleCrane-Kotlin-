package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onPerform

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onPerform.node.BOnPerformActionNode
import com.orego.battlecrane.bc.api.model.contract.BAction

@BContextComponent
class BOnPerformActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ACTION_PERFORM_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnPerformActionNode(context))
    }

    open class OnActionPerformedEvent(val action : BAction) : BActionPipe.ActionEvent()
}