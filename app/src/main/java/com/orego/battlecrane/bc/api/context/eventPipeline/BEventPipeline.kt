package com.orego.battlecrane.bc.api.context.eventPipeline

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.BHitPointPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.BLevelPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.BProducePipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.BUnitPipe
import java.lang.IllegalStateException

class BEventPipeline(context: BGameContext) {

    private var isWorking = false

    private val eventQueue = mutableListOf<BEvent>()

    private val pipeMap = mutableMapOf<Long, BPipe>()

    /**
     * Adds root pipes:
     */

    init {
        this.connectPipe(BUnitPipe(context))
        this.connectPipe(BActionPipe(context))
        this.connectPipe(BAttackPipe(context))
        this.connectPipe(BHitPointPipe(context))
        this.connectPipe(BLevelPipe(context))
        this.connectPipe(BProducePipe(context))
    }

    fun pushEvent(event: BEvent?) {
        if (event != null) {
            if (!this.isWorking) {
                this.isWorking = true
                val pipes = this.pipeMap.values.toList()
                for (i in 0 until pipes.size) {
                    pipes[i].push(event)
                }
                this.isWorking = false
                if (!this.eventQueue.isEmpty()) {
                    val nextEvent = this.eventQueue.removeAt(0)
                    this.pushEvent(nextEvent)
                }
            } else {
                this.eventQueue.add(event)
            }
        }
    }

    fun connectPipe(pipe: BPipe) {
        this.pipeMap[pipe.id] = pipe
    }

    fun findPipe(name: String): BPipe {
        for (pipe in this.pipeMap.values) {
            val result = pipe.findPipe(name)
            if (result != null) {
                return result
            }
        }
        throw IllegalStateException("Pipe not found!")
    }

    fun findNode(name: String): BNode {
        for (pipe in this.pipeMap.values) {
            val node = pipe.findNode(name)
            if (node != null) {
                return node
            }
        }
        throw IllegalStateException("Node not found!")
    }

    operator fun get(name: String) = this.pipeMap.values
        .find { it.name == name }

    fun bindPipeTo(nodeName: String, pipe: BPipe) {
        this.findNode(nodeName).connectPipe(pipe)
    }
}