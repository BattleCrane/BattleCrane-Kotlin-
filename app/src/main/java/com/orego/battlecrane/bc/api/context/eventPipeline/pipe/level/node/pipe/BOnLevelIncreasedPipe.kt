package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.BLevelPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.node.BLevelNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.node.pipe.node.BOnLevelIncreasedNode
import com.orego.battlecrane.bc.api.model.contract.BLevelable

class BOnLevelIncreasedPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "${BLevelNode.NAME}/ON_LEVEL_INCREASED_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BOnLevelIncreasedNode(context))

    open class OnLevelIncreasedBundle(levelable : BLevelable, val range : Int) : BLevelPipe.LevelBundle(levelable)
}