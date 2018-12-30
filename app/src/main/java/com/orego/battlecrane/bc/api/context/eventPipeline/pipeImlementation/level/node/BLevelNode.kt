package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.level.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.level.BLevelPipe

class BLevelNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "LEVEL_NODE"
    }

    override val name = NAME

    override fun handle(event: BEvent) : BEvent? {
        return if (event.bundle is BLevelPipe.LevelBundle) {
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}