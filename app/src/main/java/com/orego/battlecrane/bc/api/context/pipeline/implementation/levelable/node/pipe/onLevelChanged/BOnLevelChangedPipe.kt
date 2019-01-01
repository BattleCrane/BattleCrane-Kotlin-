package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelChanged

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelChanged.node.BOnLevelChangedNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent

@BContextComponent
class BOnLevelChangedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_LEVEL_CHANGED_PIPE"

        fun createOnLevelIncreasedEvent(levelableId: Long, range: Int) =
            OnLevelIncreasedEvent(levelableId, range)

        fun createOnLevelDecreasedEvent(levelableId: Long, range: Int) =
            OnLevelDecreasedEvent(levelableId, range)

        fun createOnLevelChangedEvent(levelableId: Long, range: Int) =
            OnLevelChangedEvent(levelableId, range)
    }

    override val name = NAME

    init {
        this.placeNode(BOnLevelChangedNode(context))
    }

    /**
     * Event.
     */

    abstract class OnLevelActionEvent(val levelableId: Long, val range: Int) :
        BLevelablePipe.LevelableEvent()

    open class OnLevelIncreasedEvent(levelableId: Long, range: Int) :
        OnLevelActionEvent(levelableId, range)

    open class OnLevelDecreasedEvent(levelableId: Long, range: Int) :
        OnLevelActionEvent(levelableId, range)

    open class OnLevelChangedEvent(levelableId: Long, range: Int) :
        OnLevelActionEvent(levelableId, range)
}