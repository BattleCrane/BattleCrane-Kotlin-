package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BLevelableHeap

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
        BLevelablePipe.Event() {

        abstract fun perform(context: BGameContext) : Boolean
    }

    open class OnIncreasedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range) {

        override fun perform(context: BGameContext): Boolean {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            val hasIncreased = range > 0 && levelable.currentLevel < levelable.maxLevel
            if (hasIncreased) {
                levelable.currentLevel += range
            }
            return hasIncreased
        }
    }

    open class OnDecreasedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range) {

        override fun perform(context: BGameContext): Boolean {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            val hasDecreased = this.range > 0 && levelable.currentLevel > 1
            if (hasDecreased) {
                val newLevel = levelable.currentLevel - range
                if (newLevel > 1) {
                    levelable.currentLevel = newLevel
                } else {
                    levelable.currentLevel = 1
                }
            }
            return hasDecreased
        }
    }

    open class OnChangedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range) {

        override fun perform(context: BGameContext): Boolean {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            val hasChanged = this.range > 0 && this.range <= levelable.maxLevel
            if (hasChanged) {
                levelable.currentLevel = this.range
            }
            return hasChanged
        }
    }
}