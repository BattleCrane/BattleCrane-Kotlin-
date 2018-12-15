package com.orego.battlecrane.bc.api.context.pipeline

class BPipeline {

    private val pipeMap = mutableMapOf<String, Pipe>()

    fun push(any: List<Any?>) {
        any.forEach { this.push(it) }
    }

    fun push(any: Any?) {
        if (any != null) {
            this.pipeMap.values.forEach { it.push(any) }
        }
    }

    fun add(pipe: Pipe) {
        this.pipeMap[pipe.name] = pipe
    }

    operator fun get(name: String) = this.pipeMap[name]

    class Pipe(val name: String) {

        private val nodes = mutableListOf<Node>()

        fun push(any: Any?) {
            val startPosition = 0
            this.push(any, startPosition)
        }

        private fun push(any: Any?, position: Int) {
            if (any != null) {
                if (position < this.nodes.size) {
                    val newAny = this.nodes[position].handle(any)
                    this.push(newAny, position + 1)
                }
            }
        }

        abstract class Node(val name: String) {

            private val pipeMap = mutableMapOf<String, Pipe>()

            abstract fun handle(any: Any?): Any?

            fun add(pipe: Pipe) {
                this.pipeMap[pipe.name] = pipe
            }
        }
    }
}