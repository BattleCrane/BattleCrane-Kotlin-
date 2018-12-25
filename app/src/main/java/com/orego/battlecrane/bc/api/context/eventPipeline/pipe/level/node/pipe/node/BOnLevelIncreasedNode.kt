package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.node.pipe.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.node.pipe.BOnLevelIncreasedPipe
import com.orego.battlecrane.bc.api.model.contract.BLevelable

class BOnLevelIncreasedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "${BOnLevelIncreasedPipe.NAME}/ON_LEVEL_INCREASED_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent): BEvent? {
        val bundle = event.bundle
        if (bundle is BOnLevelIncreasedPipe.OnLevelIncreasedBundle) {
            if (this.increaseLevel(bundle.levelable, bundle.range)) {
                this.pipeMap.values.forEach { it.push(event) }
                return event
            }
        }
        return null
    }

    fun increaseLevel(levelable: BLevelable, range: Int): Boolean {
        val hasIncreased = range > 0 && levelable.currentLevel < levelable.maxLevel
        if (hasIncreased) {
            levelable.currentLevel += range
        }
        return hasIncreased
    }
}