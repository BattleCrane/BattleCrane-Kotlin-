package com.orego.battlecrane.bc.api.context.eventPipeline

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.util.EventUtil

class BEventPipeline(context: BGameContext) {

    private var isWorking = false

    private val eventQueue = mutableListOf<BEvent>()

    private val pipeMap = mutableMapOf(
        BUnitPipe.NAME to BUnitPipe(context),
        BActionPipe.NAME to BActionPipe(context),
        BAttackPipe.NAME to BAttackNode(context)
    )

    fun pushEvent(event: BEvent?) {
        if (EventUtil.isValid(event)) {
            if (!this.isWorking) {
                this.isWorking = true
                this.pipeMap.values.forEach { it.push(event) }
                this.isWorking = false
                if (!this.eventQueue.isEmpty()) {
                    val nextEvent = this.eventQueue.removeAt(0)
                    this.pushEvent(nextEvent)
                }
            } else {
                this.eventQueue.add(event!!)
            }
        }
    }

    fun addPipe(pipe: Pipe) {
        this.pipeMap[pipe.name] = pipe
    }

    fun findNode(name: String): Pipe.Node? {
        for (pipe in this.pipeMap.values) {
            val node = pipe.findNode(name)
            if (node != null) {
                return node
            }
        }
        return null
    }

    fun findPipe(name: String): Pipe? {
        for (pipe in this.pipeMap.values) {
            val result = pipe.findPipe(name)
            if (result != null) {
                return result
            }
        }
        return null
    }

    operator fun get(name: String) = this.pipeMap[name]

    /**
     * Provides events to nodes.
     */

    abstract class Pipe(protected val context: BGameContext) {

        abstract val name: String

        protected abstract val nodes: MutableList<Node>

        fun push(any: BEvent?) {
            val startPosition = 0
            return this.push(any, startPosition)
        }

        private fun push(event: BEvent?, position: Int) {
            if (EventUtil.isValid(event)) {
                if (position < this.nodes.size) {
                    val nextEvent = this.nodes[position].handle(event!!)
                    this.push(nextEvent, position + 1)
                }
            }
        }

        fun addNode(node: Node) {
            this.nodes.add(node)
        }

        fun findNode(name: String): Node? {
            for (node in this.nodes) {
                val result = node.findNode(name)
                if (result != null) {
                    return result
                }
            }
            return null
        }

        fun findPipe(name: String) : Pipe? {
            if (this.name == name) {
                return this
            } else {
                for (node in this.nodes) {
                    val result = node.findPipe(name)
                    if (result != null){
                        return result
                    }
                }
                return null
            }
        }

        /**
         * Handles events.
         */

        abstract class Node(protected val context: BGameContext) {

            abstract val name: String

            protected val pipeMap = mutableMapOf<String, Pipe>()

            abstract fun handle(event: BEvent) : BEvent?

            fun addPipe(pipe: Pipe) {
                this.pipeMap[pipe.name] = pipe
            }

            fun findNode(name: String): Node? {
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

            fun findPipe(name : String) : Pipe? {
                for (pipe in this.pipeMap.values) {
                    val result = pipe.findPipe(name)
                    if (result != null) {
                        return result
                    }
                }
                return null
            }
        }
    }
}