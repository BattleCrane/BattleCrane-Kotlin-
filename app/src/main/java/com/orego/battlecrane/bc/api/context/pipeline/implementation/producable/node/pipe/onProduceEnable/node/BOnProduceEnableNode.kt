package com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BProducableHeap

@BContextComponent
class BOnProduceEnableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_PRODUCE_ENABLE_NODE"
    }

    override val name = NAME

    /**
     * Context.
     */

    private val producableHeap by lazy {
        this.context.storage.getHeap(BProducableHeap::class.java)
    }

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.OnProducableEnableEvent) {
            if (this.switchProduceEnable(event.producableId, event.isEnable)) {
                this.pushEventIntoPipes(event)
                return event
            }
        }
        return null
    }

    private fun switchProduceEnable(producableId: Long, isProduceEnable: Boolean): Boolean {
        val producable = this.producableHeap[producableId]!!
        val isSuccessful = producable.isProduceEnable != isProduceEnable
        if (isSuccessful) {
            producable.isProduceEnable = isProduceEnable
        }
        return isSuccessful
    }
}