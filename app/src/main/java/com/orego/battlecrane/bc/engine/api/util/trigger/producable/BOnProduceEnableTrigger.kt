package com.orego.battlecrane.bc.engine.api.util.trigger.producable

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node.BOnProduceEnableNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BProducableHeap
import com.orego.battlecrane.bc.engine.api.model.property.BProducable

class BOnProduceEnableTrigger private constructor(context: BGameContext, val producable: BProducable) :
    BNode(context) {

    private val map = context.storage.getHeap(BProducableHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.Event
            && this.producable.producableId == event.producableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            this.pushToInnerPipes(event)
            return event
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.map.containsKey(this.producable.producableId)

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val producable = this@BOnProduceEnableTrigger.producable

        override fun isFinished() = this@BOnProduceEnableTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, producable: BProducable) {
            BOnProduceEnableNode.connect(context) {
                BOnProduceEnableTrigger(
                    context,
                    producable
                )
            }
        }
    }
}