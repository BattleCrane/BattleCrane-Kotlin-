package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BLevelableHeap

class BOnLevelActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_LEVEL_ACTION_PIPE"

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

        abstract fun isEnable(context: BGameContext): Boolean

        abstract fun perform(context: BGameContext)
    }

    open class OnIncreasedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range) {

        override fun isEnable(context: BGameContext): Boolean {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            return this.range > 0 && levelable.currentLevel < levelable.maxLevel
        }

        override fun perform(context: BGameContext) {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            levelable.currentLevel += this.range
        }
    }

    open class OnDecreasedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range) {

        override fun isEnable(context: BGameContext): Boolean {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            return this.range > 0 && levelable.currentLevel > 1
        }

        override fun perform(context: BGameContext) {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            val newLevel = levelable.currentLevel - this.range
            if (newLevel > 1) {
                levelable.currentLevel = newLevel
            } else {
                levelable.currentLevel = 1
            }
        }
    }

    open class OnChangedEvent(levelableId: Long, range: Int) :
        Event(levelableId, range) {

        override fun isEnable(context: BGameContext): Boolean {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            return this.range > 0 && this.range <= levelable.maxLevel
        }

        override fun perform(context: BGameContext) {
            val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
            levelable.currentLevel = this.range
        }
    }
}