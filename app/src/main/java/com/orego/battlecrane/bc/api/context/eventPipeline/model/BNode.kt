package com.orego.battlecrane.bc.api.context.eventPipeline.model

import com.orego.battlecrane.bc.api.context.BGameContext

/**
 * Handles events.
 */

abstract class BNode(protected val context: BGameContext) {

    abstract val name: String

    protected val pipeMap = mutableMapOf<String, BPipe>()

    abstract fun handle(event: BEvent): BEvent?

    open fun connectPipe(pipe: BPipe) {
        this.pipeMap[pipe.name] = pipe
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
}