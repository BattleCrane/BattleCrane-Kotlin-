package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.node.BLevelNode
import com.orego.battlecrane.bc.api.model.contract.BLevelable

class BLevelPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "LEVEL_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BLevelNode(context))

    open class LevelBundle(val levelable : BLevelable)
}