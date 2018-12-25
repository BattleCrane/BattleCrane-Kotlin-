package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.node.BProduceNode
import com.orego.battlecrane.bc.api.model.contract.BProducable

class BOnProduceEnablePipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "${BProduceNode.NAME}/ON_PRODUCE_ENABLE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BOnProduceEnableNode(context))

    open class OnProduceEnableBundle(val producable : BProducable)
}