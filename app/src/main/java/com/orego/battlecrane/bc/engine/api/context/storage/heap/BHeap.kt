package com.orego.battlecrane.bc.engine.api.context.storage.heap

/**
 * Keeps game context componets by id.
 */

abstract class BHeap<T> {

    val objectMap = mutableMapOf<Long, T>()

    operator fun get(id: Long) : T = this.objectMap[id]!!

    abstract fun onPutObject(any: Any)

    abstract fun onRemoveObject(any: Any)

    fun getObjectList() = this.objectMap.values.toList()
}