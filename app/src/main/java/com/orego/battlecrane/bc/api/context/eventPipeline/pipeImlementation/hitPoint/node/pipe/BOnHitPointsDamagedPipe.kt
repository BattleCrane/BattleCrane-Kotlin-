package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.BHitPointPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.node.BHitPointNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.hitPoint.node.pipe.node.BOnHitPointsDamagedNode
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

@BContextComponent
class BOnHitPointsDamagedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_DAMAGED_PIPE"

        fun createEvent(hitPointableId: Long, damage: Int) =
            OnHitPointsDamagedEvent(hitPointableId, damage)
    }

    override val name = NAME

    init {
        this.placeNode(BOnHitPointsDamagedNode(context))
    }

    open class OnHitPointsDamagedEvent(val hitPointableId: Long, val damage: Int) :
        BHitPointPipe.HitPointableEvent()
}