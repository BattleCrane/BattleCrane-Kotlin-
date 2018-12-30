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

    open fun connectPipe(pipe: BPipe) {
        this.pipeMap[pipe.id] = pipe
    }

    fun findNode(name: String): BNode? {
        if (this.name == name) {
            return this
        } else {
            for (pipe in this.pipeMap.values) {
                val result = pipe.findNode(name)
                if (result != null) {
                    return result
                }
            }
            return null
        }
    }

    fun findPipe(name: String): BPipe? {
        for (pipe in this.pipeMap.values) {
            val result = pipe.findPipe(name)
            if (result != null) {
                return result
            }
        }
        return null
    }

    protected fun pushEventIntoPipes(event : BEvent) {
        val pipes = this.pipeMap.values.toList()
        val size  = pipes.size
        for (i in 0 until size) {
            pipes[i].push(event)
        }
    }
}