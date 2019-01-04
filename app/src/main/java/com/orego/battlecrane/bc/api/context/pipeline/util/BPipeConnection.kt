package com.orego.battlecrane.bc.api.context.pipeline.util

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode

data class BPipeConnection(val sourcePipeId: Long, val destinationNodeId : Long) {

    companion object {

        fun createByNode(context: BGameContext, destinationNodeName: String, sourceNode: BNode): BPipeConnection {
            val sourcePipe = sourceNode.wrapInPipe()
            val destinationNode = context.pipeline.bindPipeToNode(destinationNodeName, sourcePipe)
            return BPipeConnection(sourcePipe.id, destinationNode.id)
        }
    }

    fun disconnect(context: BGameContext) {
        context.pipeline.unbindPipeFromNode(this.sourcePipeId, this.destinationNodeId)
    }
}