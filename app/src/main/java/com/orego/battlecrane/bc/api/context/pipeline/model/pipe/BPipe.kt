package com.orego.battlecrane.bc.api.context.pipeline.model.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.generator.BIdGenerator

/**
 * Provides events to nodes.
 */

open class BPipe(
    protected val context: BGameContext,
    open val nodes: MutableList<BNode> = mutableListOf()
) {

    val id = this.context.idGenerator.generatePipeId()

    open val name: String? = null

    fun push(any: BEvent?) {
        val startPosition = 0
        return this.push(any, startPosition)
    }

    private fun push(event: BEvent?, position: Int) {
        if (event != null) {
            if (position < this.nodes.size) {
                val nextEvent = this.nodes[position].handle(event)
                this.push(nextEvent, position + 1)
            }
        }
    }

    fun placeNode(node: BNode) {
        this.nodes.add(node)
    }

    fun findNodeBy(condition: (BNode) -> Boolean): BNode? {
        for (i in 0 until this.nodes.size) {
            val result = this.nodes[i].findNodeBy(condition)
            if (result != null) {
                return result
            }
        }
        return null
    }

    fun findPipeBy(condition: (BPipe) -> Boolean): BPipe? {
        if (condition(this)) {
            return this
        } else {
            for (i in 0 until this.nodes.size) {
                val result = this.nodes[i].findPipeBy(condition)
                if (result != null) {
                    return result
                }
            }
            return null
        }
    }
}