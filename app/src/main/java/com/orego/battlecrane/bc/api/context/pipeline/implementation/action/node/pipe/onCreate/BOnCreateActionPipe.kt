package com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onCreate

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.BActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.action.node.pipe.onCreate.node.BOnCreateActionNode

@BContextComponent
class BOnCreateActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_CREATE_ACTION_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnCreateActionNode(context))
    }

    open class OnCreateActionEvent: BActionPipe.ActionEvent()
}