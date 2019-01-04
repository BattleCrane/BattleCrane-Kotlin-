package com.orego.battlecrane.bc.api.context.pipeline

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.BProducablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BPipeline(context: BGameContext) {

    private var isWorking = false

    private val eventQueue = mutableListOf<BEvent>()

    private val pipeMap = mutableMapOf<Long, BPipe>()

    private val onPipelineWorkFinshedObserver =
        mutableMapOf<Class<out OnPipelineWorkFinishedListener>, OnPipelineWorkFinishedListener>()

    /**
     * Adds root pipes:
     */

    init {
        this.connectInnerPipe(BUnitPipe(context))
        this.connectInnerPipe(BAttackablePipe(context))
        this.connectInnerPipe(BHitPointablePipe(context))
        this.connectInnerPipe(BLevelablePipe(context))
        this.connectInnerPipe(BProducablePipe(context))
        this.connectInnerPipe(BTurnPipe(context))
    }

    /**
     * TrainMarineEvent.
     */

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
                } else {
                    this.onPipelineWorkFinshedObserver.values.forEach { listener ->
                        listener.onPipelineWorkFinished()
                    }
                }
            } else {
                this.eventQueue.add(event)
            }
        }
    }

    /**
     * Pipe.
     */

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

    /**
     * Node.
     */

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

    fun bindPipeToNode(nodeName: String, pipe: BPipe) : BNode {
        val node = this.findNode(nodeName)
        node.connectInnerPipe(pipe)
        return node
    }

    fun bindPipeToNode(nodeId: Long, pipe: BPipe) {
        this.findNode(nodeId).connectInnerPipe(pipe)
    }

    fun unbindPipeFromNode(nodeName: String, pipeId : Long) {
        this.findNode(nodeName).disconnectInnerPipe(pipeId)
    }

    fun unbindPipeFromNode(nodeId: Long, pipeId : Long) {
        this.findNode(nodeId).disconnectInnerPipe(pipeId)
    }

    fun bindNodeToPipe(pipeId : Long, node : BNode) {
        this.findPipe(pipeId).placeNode(node)
    }

    /**
     * Listener.
     */

    fun registerOnPipelineWorkFinishedListener(listener: OnPipelineWorkFinishedListener) {
        this.onPipelineWorkFinshedObserver[listener::class.java] = listener
    }

    fun unregisterOnPipelineWorkFinishedListener(clazz: Class<out OnPipelineWorkFinishedListener>) {
        this.onPipelineWorkFinshedObserver.remove(clazz)
    }

    interface OnPipelineWorkFinishedListener {

        fun onPipelineWorkFinished()
    }
}