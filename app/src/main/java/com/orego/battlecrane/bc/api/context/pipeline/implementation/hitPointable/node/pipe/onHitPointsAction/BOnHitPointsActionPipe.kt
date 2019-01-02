package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BOnHitPointsActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_ACTION_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnHitPointsActionNode(context))
    }

    /**
     * Event.
     */

    open class Event(val hitPointableId: Long, val range: Int) :
        BHitPointablePipe.Event()

    object Current {

        fun createOnIncreasedEvent(hitPointableId: Long, range: Int) =
            OnIncreasedEvent(hitPointableId, range)

        fun createOnDecreasedEvent(hitPointableId: Long, range: Int) =
            OnDecreasedEvent(hitPointableId, range)

        fun createOnChangedEvent(hitPointableId: Long, range: Int) =
            OnChangedEvent(hitPointableId, range)

        open class OnIncreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range)

        open class OnChangedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range)

        open class OnDecreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range)
    }

    object Max {

        fun createOnIncreasedEvent(hitPointableId: Long, range: Int) =
            OnIncreasedEvent(hitPointableId, range)

        fun createOnDecreasedEvent(hitPointableId: Long, range: Int) =
            OnDecreasedEvent(hitPointableId, range)

        fun createOnChangedEvent(hitPointableId: Long, range: Int) =
            OnChangedEvent(hitPointableId, range)

        open class OnIncreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range)

        open class OnChangedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range)

        open class OnDecreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range)
    }
}