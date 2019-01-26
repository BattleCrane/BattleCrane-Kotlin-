package com.orego.battlecrane.bc.engine.api.util.pipe

import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

open class BParentPipe(val innerNode: BNode) : BPipe(innerNode.context, mutableListOf(innerNode)) {

    override fun isFinished() = this.innerNode.isFinished()
}