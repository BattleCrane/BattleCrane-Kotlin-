package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node.BHitPointNode
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

class BHitPointPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "HIT_POINT_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BHitPointNode(context))

    open class HitPointBundle(val hitPointable : BHitPointable)
}