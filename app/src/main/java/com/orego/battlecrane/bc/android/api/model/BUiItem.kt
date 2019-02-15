package com.orego.battlecrane.bc.android.api.model

import com.orego.battlecrane.bc.android.api.context.BUiGameContext

abstract class BUiItem<T> protected constructor(open val item: T) {

    /**
     * Creates a item.
     */

    abstract class Builder<T> {

        abstract fun build(uiGameContext: BUiGameContext, item: T): BUiItem<T>
    }

    /**
     * Creates a item by builder map.
     */

    open class BUiItemFactory<T : Any> {

        private val builderMap: MutableMap<Class<out T>, Builder<T>> = mutableMapOf()

        fun addBuilder(entry: Map.Entry<Class<out T>, Builder<T>>) {
            val clazz = entry.key
            if (!this.builderMap.containsKey(clazz)) {
                this.builderMap[clazz] = entry.value
            }
        }

        fun addBuilder(clazz: Class<out T>, builder: Builder<T>) {
            if (!this.builderMap.containsKey(clazz)) {
                this.builderMap[clazz] = builder
            }
        }

        open fun build(uiGameContext: BUiGameContext, item: T): BUiItem<T> {
            val builder = this.builderMap[item::class.java]!!
            val holder = builder.build(uiGameContext, item)
            uiGameContext.gameContext.storage.putObject(holder)
            return holder
        }
    }
}