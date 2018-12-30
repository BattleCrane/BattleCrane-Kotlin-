package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.produce.node.pipe.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.produce.node.pipe.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.model.contract.BProducable

class BOnProduceEnableNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "${BOnProduceEnablePipe.NAME}/ON_PRODUCE_ENABLE_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        val bundle = event.bundle
        if (bundle is BOnProduceEnablePipe.OnProduceEnableBundle) {
            if (this.switchProduceEnable(bundle.producable, bundle.isEnable)) {
                this.pipeMap.values.forEach { it.push(event) }
                return event
            }
        }
        return null
    }

    fun switchProduceEnable(producable: BProducable, isProduceEnable: Boolean): Boolean {
        val isSuccessful = producable.isProduceEnable != isProduceEnable
        if (isSuccessful) {
            producable.isProduceEnable = isProduceEnable
        }
        return isSuccessful
    }
}