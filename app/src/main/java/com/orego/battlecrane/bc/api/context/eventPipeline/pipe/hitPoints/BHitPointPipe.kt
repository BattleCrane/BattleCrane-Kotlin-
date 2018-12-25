package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoints

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoints.node.BHitPointNode
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

class BHitPointPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "HIT_POINT_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BHitPointNode(context))

    open class HitPointBundle(val hitPointable : BHitPointable)
}