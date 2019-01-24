package com.orego.battlecrane.bc.engine.api.context.pipeline

import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

/**
 * Represents a extendable tree of pipes and nodes.
 */

class BPipeline {

    private var isLocking = false

    private val eventQueue = mutableListOf<BEvent>()

    private val pipeMap = mutableMapOf<Long, BPipe>()

    private val onPipelineWorkFinshedObserver:
            MutableMap<Class<out OnPipelineWorkFinishedListener>, OnPipelineWorkFinishedListener> = mutableMapOf()

    private val onBroadcastEventObserver:
            MutableMap<Class<out OnBroadcastEventListener>, OnBroadcastEventListener> = mutableMapOf()

    /**
     * Event.
     */

    fun broacastEvent(event: BEvent) {
        this.pushEvent(event)
        this.onBroadcastEventObserver.values.forEach { listener ->
            listener.onBroadcastEvent(event)
        }
    }

    fun pushEvent(event: BEvent?) {
        if (event != null) {
            if (!this.isLocking) {
                //Lock process:
                this.isLocking = true
                //Handle event:
                val pipes = this.pipeMap.values.toList()
                for (i in 0 until pipes.size) {
                    pipes[i].push(event)
                }
                //Clear unused pipes & nodes:
                this.removeUnusedComponents()
                //Unlock process:
                this.isLocking = false
                //Check rest events in collection:
                if (!this.eventQueue.isEmpty()) {
                    val nextEvent = this.eventQueue.removeAt(0)
                    this.pushEvent(nextEvent)
                } else {
                    //Notify listeners about pipeline work finished:
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
     * Find.
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

    /**
     * Bind.
     */

    fun bindPipeToNode(nodeName: String, pipe: BPipe): BNode {
        val node = this.findNode(nodeName)
        node.connectInnerPipe(pipe)
        return node
    }

    fun bindPipeToNode(nodeId: Long, pipe: BPipe) {
        this.findNode(nodeId).connectInnerPipe(pipe)
    }

    fun unbindPipeFromNode(nodeName: String, pipeId: Long) {
        this.findNode(nodeName).disconnectInnerPipe(pipeId)
    }

    fun unbindPipeFromNode(nodeId: Long, pipeId: Long) {
        this.findNode(nodeId).disconnectInnerPipe(pipeId)
    }

    fun bindNodeToPipe(pipeId: Long, node: BNode) {
        this.findPipe(pipeId).placeNode(node)
    }

    /**
     * Remove.
     */

    fun removeUnusedComponents() {
        val pipes = this.pipeMap.values.toList()
        for (i in 0 until pipes.size) {
            val pipe = pipes[i]
            pipe.removeUnusedComponents()
            if (pipe.isFinished()) {
                this.pipeMap.remove(pipe.id)
            }
        }
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

    interface OnBroadcastEventListener {

        fun onBroadcastEvent(event: BEvent?)
    }
}