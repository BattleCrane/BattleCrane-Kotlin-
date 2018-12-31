package com.orego.battlecrane.bc.api.context.pipeline.implementation.produce

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.produce.node.BProduceNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent

@BContextComponent
class BProducePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "PRODUCE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BProduceNode(context))

    open class ProduceEvent : BEvent()
}