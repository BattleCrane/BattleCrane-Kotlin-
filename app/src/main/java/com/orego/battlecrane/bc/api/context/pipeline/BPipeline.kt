package com.orego.battlecrane.bc.api.context.pipeline

class BPipeline {

    private val pipeMap = mutableMapOf<String, Pipe>()

    fun handle(event: BEvent?) {
        if (isValidEvent(event)) {
            this.pipeMap.values.forEach { it.handle(event) }
        }
    }

    fun add(pipe: Pipe) {
        this.pipeMap[pipe.name] = pipe
    }

    operator fun get(name: String) = this.pipeMap[name]


    class Pipe(val name: String) {

        private val nodes = mutableListOf<Node>()

        fun handle(any: BEvent?) {
            val startPosition = 0
            return this.handle(any, startPosition)
        }

        private fun handle(event: BEvent?, position: Int) {
            if (isValidEvent(event)) {
                if (position < this.nodes.size) {
                    this.nodes[position].handle(event)
                    this.handle(event, position + 1)
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