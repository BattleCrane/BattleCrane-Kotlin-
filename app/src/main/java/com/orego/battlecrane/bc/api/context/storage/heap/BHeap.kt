package com.orego.battlecrane.bc.api.context.storage.heap

abstract class BHeap<T> {

    protected val objectMap = mutableMapOf<Long, T>()

    operator fun get(id: Long) = this.objectMap[id]

    abstract fun addObject(any: Any)

    abstract fun removeObject(any: Any)
}