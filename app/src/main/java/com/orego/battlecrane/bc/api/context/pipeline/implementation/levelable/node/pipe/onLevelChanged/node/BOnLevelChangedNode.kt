package com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelChanged.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelChanged.BOnLevelChangedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BLevelableHeap

@BContextComponent
class BOnLevelChangedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_LEVEL_CHANGED_NODE"
    }

    override val name = NAME

    /**
     * Context.
     */

    private val levelableHeap by lazy {
        this.context.storage.getHeap(BLevelableHeap::class.java)
    }

    /**
     * Handler function.
     */

    private val increaseLevelFunc: (levelableId: Long, range: Int) -> Boolean = { levelableId, range ->
        val levelable = this.levelableHeap[levelableId]!!
        val hasIncreased = range > 0 && levelable.currentLevel < levelable.maxLevel
        if (hasIncreased) {
            levelable.currentLevel += range
        }
        hasIncreased
    }

    private val decreaseLevelFunc: (levelableId: Long, range: Int) -> Boolean = { levelableId, range ->
        val levelable = this.levelableHeap[levelableId]!!
        val hasDecreased = range > 0 && levelable.currentLevel > 1
        if (hasDecreased) {
            val newLevel = levelable.currentLevel - range
            if (newLevel > 1) {
                levelable.currentLevel = newLevel
            } else {
                levelable.currentLevel = 1
            }
        }
        hasDecreased
    }

    private val changeLevelFunc: (Long, Int) -> Boolean = { levelableId, newLevel ->
        val levelable = this.levelableHeap[levelableId]!!
        val hasChanged = newLevel > 0 && newLevel <= levelable.maxLevel
        if (hasChanged) {
            levelable.currentLevel = newLevel
        }
        hasChanged
    }

    /**
     * Function map.
     */

    val eventHandlerFuncMap = mutableMapOf<Class<*>, (Long, Int) -> Boolean>(
        BOnLevelChangedPipe.OnLevelIncreasedEvent::class.java to this.increaseLevelFunc,
        BOnLevelChangedPipe.OnLevelDecreasedEvent::class.java to this.decreaseLevelFunc,
        BOnLevelChangedPipe.OnLevelChangedEvent::class.java to this.changeLevelFunc
    )

    override fun handle(event: BEvent): BEvent? {
        val handlerFunc = this.eventHandlerFuncMap[event::class.java]
        if (handlerFunc != null && event is BOnLevelChangedPipe.OnLevelActionEvent) {
            if (handlerFunc(event.levelableId, event.range)) {
                this.pushEventIntoPipes(event)
                return event
            }
        }
        return null
    }
}