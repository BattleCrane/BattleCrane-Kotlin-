package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.context.pipeline.BPipeline

interface BProducable {

    var isProduceEnable : Boolean

    /**
     * Send actions to pipeline.
     */

    fun produceAbilities(pipeline: BPipeline)
}