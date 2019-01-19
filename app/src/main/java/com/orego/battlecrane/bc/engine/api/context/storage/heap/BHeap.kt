package com.orego.battlecrane.bc.engine.api.context.storage.heap

abstract class BHeap<T> {

    val objectMap = mutableMapOf<Long, T>()

    operator fun get(id: Long) : T = this.objectMap[id]!!

    abstract fun addObject(any: Any)

    abstract fun removeObject(any: Any)

    fun getObjectList() = this.objectMap.values.toList()
}