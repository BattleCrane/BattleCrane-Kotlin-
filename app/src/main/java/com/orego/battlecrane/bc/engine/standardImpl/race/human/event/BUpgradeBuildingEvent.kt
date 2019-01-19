package com.orego.battlecrane.bc.engine.standardImpl.race.human.event

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceAction.BOnProduceActionPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BLevelableHeap
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.BHumanBuilding

open class BUpgradeBuildingEvent(producableId: Long, val levelableId: Long) : BOnProduceActionPipe.Event(producableId) {

    companion object {

        const val RANGE = 1
    }

    open fun isEnable(context: BGameContext): Boolean {
        val levelable = context.storage.getHeap(BLevelableHeap::class.java)[this.levelableId]
        return levelable is BHumanBuilding && levelable.currentLevel + RANGE <= levelable.maxLevel
    }

    fun perform(pipeline: BPipeline) {
        pipeline.pushEvent(
            BOnLevelActionPipe.createOnLevelIncreasedEvent(this.levelableId, RANGE)
        )
    }
}