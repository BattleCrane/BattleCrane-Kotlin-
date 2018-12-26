package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BActionNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node.BOnCreateActionNode

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