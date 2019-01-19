package com.orego.battlecrane.bc.engine.api.util.trigger.turn

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BProducableHeap
import com.orego.battlecrane.bc.engine.api.model.property.BProducable

class BProduceEnableOnTurnTrigger private constructor(context: BGameContext, var producable: BProducable) :
    BOnTurnTrigger(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val producableMap = context.storage.getHeap(BProducableHeap::class.java).objectMap

    override var playerId: Long
        get() = this.producable.playerId
        set(value) {
            this.producable.playerId = value
        }

    override fun onTurnStarted() {
        this.pipeline.pushEvent(
            BOnProduceEnablePipe.Event(this.producable.producableId, true)
        )
    }

    override fun onTurnFinished() {
        this.pipeline.pushEvent(
            BOnProduceEnablePipe.Event(this.producable.producableId, false)
        )
    }

    override fun isFinished() = !this.producableMap.containsKey(this.producable.producableId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val producable = this@BProduceEnableOnTurnTrigger.producable

        override fun isUnused() = this@BProduceEnableOnTurnTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, producable: BProducable) {
            BTurnNode.connect(context) {
                BProduceEnableOnTurnTrigger(
                    context,
                    producable
                )
            }
        }
    }
}