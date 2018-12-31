package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsChanged

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsChanged.node.BOnHitPointsChangedNode

@BContextComponent
class BOnHitPointsChangedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_CHANGED_PIPE"

        fun createOnHitPointsIncreasedEvent(hitPointableId: Long, range: Int) =
            OnHitPointsDecreasedEvent(hitPointableId, range)

        fun createOnHitPointsDecreasedEvent(hitPointableId: Long, range: Int) =
            OnHitPointsDecreasedEvent(hitPointableId, range)

        fun createOnHitPointsChangedEvent(hitPointableId: Long, range: Int) =
            OnHitPointsDecreasedEvent(hitPointableId, range)
    }

    override val name = NAME

    init {
        this.placeNode(BOnHitPointsChangedNode(context))
    }

    /**
     * Event.
     */

    open class OnHitPointsActionEvent(val hitPointableId: Long, val range: Int) :
        BHitPointablePipe.HitPointableEvent()

    open class OnHitPointsDecreasedEvent(hitPointableId: Long, range: Int) :
        OnHitPointsActionEvent(hitPointableId, range)

    open class OnHitPointsIncreasedEvent(hitPointableId: Long, range: Int) :
        OnHitPointsActionEvent(hitPointableId, range)

    open class OnHitPointsChangedEvent(hitPointableId: Long, range: Int) :
        OnHitPointsActionEvent(hitPointableId, range)
}