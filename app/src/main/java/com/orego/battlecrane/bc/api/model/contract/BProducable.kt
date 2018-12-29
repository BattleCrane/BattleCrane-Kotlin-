package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline

interface BProducable {

    var isProduceEnable : Boolean

    /**
     * Send actions to pipeline.
     */

    fun produceAbilities(pipeline: BEventPipeline)
}