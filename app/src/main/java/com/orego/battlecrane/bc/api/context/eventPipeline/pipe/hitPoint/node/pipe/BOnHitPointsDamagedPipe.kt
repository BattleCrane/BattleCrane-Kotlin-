package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.BHitPointPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node.BHitPointNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.node.pipe.node.BOnHitPointsDamagedNode
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

class BOnHitPointsDamagedPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "${BHitPointNode.NAME}/ON_HIT_POINTS_DAMAGED_PIPE"

        const val EVENT = "ON_HIT_POINTS_DAMAGED"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BOnHitPointsDamagedNode(context))

    open class OnHitPointsDamagedBundle(hitPointable: BHitPointable, val damage: Int) :
        BHitPointPipe.HitPointBundle(hitPointable)
}