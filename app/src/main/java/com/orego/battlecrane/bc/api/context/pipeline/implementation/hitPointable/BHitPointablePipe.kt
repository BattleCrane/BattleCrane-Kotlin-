package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.BHitPointableNode

@BContextComponent
class BHitPointablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "HIT_POINTABLE_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BHitPointableNode(context))
    }

    open class HitPointableEvent : BEvent()
}