package com.orego.battlecrane.bc.api.context.eventPipeline.model

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.util.BIdGenerator

/**
 * Provides events to nodes.
 */

open class BPipe(
    protected val context: BGameContext,
    open val nodes: MutableList<BNode> = mutableListOf()
) {

    val id = BIdGenerator.generatePipeId()

    open val name: String = ""

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

    fun findNode(name: String): BNode? {
        for (node in this.nodes) {
            val result = node.findNode(name)
            if (result != null) {
                return result
            }
        }
        return null
    }

    fun findPipe(name: String): BPipe? {
        if (this.name == name) {
            return this
        } else {
            for (node in this.nodes) {
                val result = node.findPipe(name)
                if (result != null) {
                    return result
                }
            }
            return null
        }
    }
}