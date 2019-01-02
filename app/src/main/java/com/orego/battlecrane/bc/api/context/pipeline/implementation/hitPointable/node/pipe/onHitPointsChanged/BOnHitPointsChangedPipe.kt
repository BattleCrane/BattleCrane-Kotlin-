package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsChanged

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsChanged.node.BOnHitPointsChangedNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BOnHitPointsChangedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_CHANGED_PIPE"

        fun createOnHitPointsIncreasedEvent(hitPointableId: Long, range: Int) =
            DecreasedEvent(hitPointableId, range)

        fun createOnDecreasedEvent(hitPointableId: Long, range: Int) =
            DecreasedEvent(hitPointableId, range)

        fun createOnHitPointsChangedEvent(hitPointableId: Long, range: Int) =
            DecreasedEvent(hitPointableId, range)
    }

    override val name = NAME

    init {
        this.placeNode(BOnHitPointsChangedNode(context))
    }

    /**
     * Event.
     */

    open class ActionEvent(val hitPointableId: Long, val range: Int) :
        BHitPointablePipe.Event()

    open class DecreasedEvent(hitPointableId: Long, range: Int) :
        ActionEvent(hitPointableId, range)

    open class IncreasedEvent(hitPointableId: Long, range: Int) :
        ActionEvent(hitPointableId, range)

    open class ChangedEvent(hitPointableId: Long, range: Int) :
        ActionEvent(hitPointableId, range)
}