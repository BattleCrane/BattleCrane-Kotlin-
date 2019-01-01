package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelChanged

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelChanged.node.BOnLevelChangedNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BOnLevelChangedPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_LEVEL_CHANGED_PIPE"

        fun createOnLevelIncreasedEvent(levelableId: Long, range: Int) =
            IncreasedEvent(levelableId, range)

        fun createOnLevelDecreasedEvent(levelableId: Long, range: Int) =
            DecreasedEvent(levelableId, range)

        fun createOnLevelChangedEvent(levelableId: Long, range: Int) =
            ChangedEvent(levelableId, range)
    }

    override val name = NAME

    init {
        this.placeNode(BOnLevelChangedNode(context))
    }

    /**
     * Event.
     */

    abstract class ActionEvent(val levelableId: Long, val range: Int) :
        BLevelablePipe.Event()

    open class IncreasedEvent(levelableId: Long, range: Int) :
        ActionEvent(levelableId, range)

    open class DecreasedEvent(levelableId: Long, range: Int) :
        ActionEvent(levelableId, range)

    open class ChangedEvent(levelableId: Long, range: Int) :
        ActionEvent(levelableId, range)
}