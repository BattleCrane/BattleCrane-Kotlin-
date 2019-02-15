package com.orego.battlecrane.bc.engine.api.context.storage

import com.orego.battlecrane.bc.engine.api.context.storage.heap.BHeap

/**
 * Keeps game context data.
 */

class BStorage {

    val heapMap = mutableMapOf<Class<*>, BHeap<*>>()

    inline fun <reified T> getHeap(heapClazz: Class<T>): T = this.heapMap[heapClazz] as T

    fun installHeap(heap : BHeap<*>) {
        this.heapMap[heap::class.java] = heap
    }

    fun putObject(any: Any) {
        val heaps = this.heapMap.values.toList()
        for (i in 0 until heaps.size) {
            heaps[i].onPutObject(any)
        }
    }

    fun removeObject(id: Long, heapClazz: Class<*>) {
        val heap = this.heapMap[heapClazz]!!
        val any: Any = heap[id]!!
        this.removeObject(any)
    }

    private fun removeObject(any: Any) {
        val heaps = this.heapMap.values.toList()
        for (i in 0 until heaps.size) {
            heaps[i].onRemoveObject(any)
        }
    }
}