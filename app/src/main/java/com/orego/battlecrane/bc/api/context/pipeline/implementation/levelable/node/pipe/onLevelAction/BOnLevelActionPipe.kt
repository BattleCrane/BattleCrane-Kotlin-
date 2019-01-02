package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BOnLevelActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_LEVEL_CHANGED_PIPE"

        fun createOnLevelIncreasedEvent(levelableId: Long, range: Int) =
            OnIncreasedEvent(levelableId, range)

        fun createOnLevelDecreasedEvent(levelableId: Long, range: Int) =
            OnDecreasedEvent(levelableId, range)

        fun createOnLevelChangedEvent(levelableId: Long, range: Int) =
            OnChangedEvent(levelableId, range)
    }

    override val name = NAME

    init {
        this.placeNode(BOnLevelActionNode(context))
    }

    /**
     * Event.
     */

    abstract class Event(val levelableId: Long, val range: Int) :
        BLevelablePipe.Event()

    open class OnIncreasedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range)

    open class OnDecreasedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range)

    open class OnChangedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range)
}