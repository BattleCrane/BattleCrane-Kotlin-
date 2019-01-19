package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.BProducablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.node.BOnProduceActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

class BOnProduceActionPipe(context: BGameContext) : BPipe(context){

    companion object {

        const val NAME = "ON_PRODUCE_ACTION_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnProduceActionNode(this.context))
    }

    open class Event(val producableId: Long) :
        BProducablePipe.Event()
}