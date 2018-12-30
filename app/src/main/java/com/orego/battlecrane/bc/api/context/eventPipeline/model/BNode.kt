package com.orego.battlecrane.bc.api.context.eventPipeline.model

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.util.BIdGenerator

/**
 * Handles game events.
 */

abstract class BNode(protected val context: BGameContext) {

    val id = BIdGenerator.generateNodeId()

    open val name: String? = null

    protected open val pipeMap = mutableMapOf<Long, BPipe>()

    abstract fun handle(event: BEvent): BEvent?

    open fun connectInnerPipe(pipe: BPipe) {
        this.pipeMap[pipe.id] = pipe
    }

    fun findNodeBy(condition: (BNode) -> Boolean): BNode? {
        if (condition(this)) {
            return this
        } else {
            val pipes = this.pipeMap.values.toList()
            for (i in 0 until pipes.size) {
                val result = pipes[i].findNodeBy(condition)
                if (result != null) {
                    return result
                }
            }
            return null
        }
    }

    fun findPipeBy(condition: (BPipe) -> Boolean): BPipe? {
        val pipes = this.pipeMap.values.toList()
        for (i in 0 until pipes.size) {
            val result = pipes[i].findPipeBy(condition)
            if (result != null) {
                return result
            }
        }
        return null
    }

    fun wrapInPipe() = BPipe(this.context, mutableListOf(this))

    protected fun pushEventIntoPipes(event: BEvent) {
        val pipes = this.pipeMap.values.toList()
        val size = pipes.size
        for (i in 0 until size) {
            pipes[i].push(event)
        }
    }
}