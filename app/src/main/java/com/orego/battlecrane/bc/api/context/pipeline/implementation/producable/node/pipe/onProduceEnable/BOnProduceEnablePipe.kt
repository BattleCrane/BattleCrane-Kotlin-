package com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.BProducablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node.BOnProduceEnableNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BProducableHeap

@BContextComponent
class BOnProduceEnablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_PRODUCE_ENABLE_PIPE"

        fun createEvent(producableId: Long, isEnable: Boolean) =
            Event(producableId, isEnable)
    }

    override val name = NAME

    init {
        this.placeNode(BOnProduceEnableNode(context))
    }

    open class Event(val producableId: Long, val isEnable: Boolean) :
        BProducablePipe.Event() {

        fun perform(context: BGameContext): Boolean {
            val producable = context.storage.getHeap(BProducableHeap::class.java)[this.producableId]
            val isSuccessful = producable.isProduceEnable != this.isEnable
            if (isSuccessful) {
                producable.isProduceEnable = this.isEnable
            }
            return isSuccessful
        }
    }
}