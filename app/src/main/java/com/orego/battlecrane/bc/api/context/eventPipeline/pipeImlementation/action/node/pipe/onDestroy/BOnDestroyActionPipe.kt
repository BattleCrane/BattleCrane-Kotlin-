package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onDestroy

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.action.node.pipe.onDestroy.node.BOnDestroyActionNode
import com.orego.battlecrane.bc.api.model.contract.BAction

@BContextComponent
class BOnDestroyActionPipe(context : BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ACTION_DESTROY_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnDestroyActionNode(context))
    }

    open class OnActionDestroyedEvent(val actionId : Long) : BActionPipe.ActionEvent()
}