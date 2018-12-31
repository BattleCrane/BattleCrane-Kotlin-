package com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onDestroy

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.BActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onDestroy.node.BOnDestroyActionNode

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