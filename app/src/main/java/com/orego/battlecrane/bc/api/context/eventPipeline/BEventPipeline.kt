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
        this.connectInnerPipe(BUnitPipe(context))
        this.connectInnerPipe(BActionPipe(context))
        this.connectInnerPipe(BAttackPipe(context))
        this.connectInnerPipe(BHitPointPipe(context))
        this.connectInnerPipe(BLevelPipe(context))
        this.connectInnerPipe(BProducePipe(context))
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

    fun connectInnerPipe(pipe: BPipe) {
        this.pipeMap[pipe.id] = pipe
    }

    fun findPipe(name: String) = this.findPipeBy { it.name == name }

    fun findPipe(id: Long) = this.findPipeBy { it.id == id }

    fun findPipeBy(condition: (BPipe) -> Boolean): BPipe {
        val pipes = this.pipeMap.values.toList()
        for (i in 0 until pipes.size) {
            val result = pipes[i].findPipeBy(condition)
            if (result != null) {
                return result
            }
        }
        throw IllegalStateException("Pipe not found!")
    }

    fun findNode(name: String) = this.findNodeBy { it.name == name }

    fun findNode(id: Long) = this.findNodeBy { it.id == id }

    fun findNodeBy(condition: (BNode) -> Boolean): BNode {
        val pipes = this.pipeMap.values.toList()
        for (i in 0 until pipes.size) {
            val node = pipes[i].findNodeBy(condition)
            if (node != null) {
                return node
            }
        }
        throw IllegalStateException("Node not found!")
    }

    fun bindPipeToNode(nodeName: String, pipe: BPipe) {
        this.findNode(nodeName).connectInnerPipe(pipe)
    }

    fun bindPipeToNode(nodeId: Long, pipe: BPipe) {
        this.findNode(nodeId).connectInnerPipe(pipe)
    }
}