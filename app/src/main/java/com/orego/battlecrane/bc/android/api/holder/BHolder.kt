package com.orego.battlecrane.bc.android.api.holder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext

abstract class BHolder<T>(open val item: T) {

    open class Factory<T : Any> {

        private val builderMap: MutableMap<Class<out T>, Builder<T>> = mutableMapOf()

        fun addBuilder(entry: Map.Entry<Class<out T>, Builder<T>>) {
            this.builderMap[entry.key] = entry.value
        }

        fun addBuilder(clazz: Class<out T>, builder: Builder<T>) {
            this.builderMap[clazz] = builder
        }

        fun build(uiGameContext: BUiGameContext, item: T): BHolder<T> {
            val builder = this.builderMap[item::class.java]!!
            val holder = builder.build(uiGameContext, item)
            uiGameContext.gameContext.storage.addObject(holder)
            return holder
        }
    }

    abstract class Builder<T> {

        abstract fun build(uiGameContext: BUiGameContext, item: T): BHolder<T>
    }
}