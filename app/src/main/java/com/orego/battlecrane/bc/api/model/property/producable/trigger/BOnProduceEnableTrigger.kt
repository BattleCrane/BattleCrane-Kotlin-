package com.orego.battlecrane.bc.api.model.property.producable.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node.BOnProduceEnableNode
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BProducableHeap
import com.orego.battlecrane.bc.api.model.property.producable.BProducable

class BOnProduceEnableTrigger private constructor(context: BGameContext, val producable: BProducable) :
    BNode(context) {

    private val map = context.storage.getHeap(BProducableHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.Event
            && this.producable.producableId == event.producableId
            && event.isEnable(this.context)
        ) {
            println("BOnProduceEnableTrigger: HANDLE!")
            println(this.producable)
            event.perform(this.context)
            this.pushToInnerPipes(event)
            return event
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.map.containsKey(this.producable.producableId)

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val producable = this@BOnProduceEnableTrigger.producable

        override fun isUnused() = this@BOnProduceEnableTrigger.isUnused()
    }

    companion object {

        fun connect(context: BGameContext, producable: BProducable) {
            println("BOnProduceEnableTrigger: CONNECT")
            BOnProduceEnableNode.connect(context) {
                BOnProduceEnableTrigger(
                    context,
                    producable
                )
            }
        }
    }
}