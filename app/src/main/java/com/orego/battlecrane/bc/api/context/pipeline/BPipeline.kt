package com.orego.battlecrane.bc.api.context.pipeline

class BPipeline {

    private val pipeMap = mutableMapOf<String, Pipe>()

    fun push(event: BEvent?) {
        if (isValidEvent(event)) {
            this.pipeMap.values.forEach { it.push(event) }
        }
    }

    fun add(pipe: Pipe) {
        this.pipeMap[pipe.name] = pipe
    }

    operator fun get(name: String) = this.pipeMap[name]


    class Pipe(val name: String) {

        private val nodes = mutableListOf<Node>()

        fun push(any: BEvent?) {
            val startPosition = 0
            return this.push(any, startPosition)
        }

        private fun push(event: BEvent?, position: Int) {
            if (isValidEvent(event)) {
                if (position < this.nodes.size) {
                    this.nodes[position].handle(event)
                    this.push(event, position + 1)
                }
            }
        }

        abstract class Node(val name: String) {

            private val pipeMap = mutableMapOf<String, Pipe>()

            abstract fun handle(event: BEvent?)

            fun add(pipe: Pipe) {
                this.pipeMap[pipe.name] = pipe
            }
        }
    }
}