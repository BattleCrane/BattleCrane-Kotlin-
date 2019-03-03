package com.orego.battlecrane.bc.android.api.model

import com.orego.battlecrane.bc.android.api.context.BUiGameContext

/**
 * Creates a item by builder map.
 */

open class BUiItemFactory<T : Any> {

    private val builderMap: MutableMap<Class<out T>, BUiItem.Builder<T>> = mutableMapOf()

    fun addBuilder(entry: Map.Entry<Class<out T>, BUiItem.Builder<T>>) {
        val clazz = entry.key
        if (!this.builderMap.containsKey(clazz)) {
            this.builderMap[clazz] = entry.value
        }
    }

    fun addBuilder(clazz: Class<out T>, builder: BUiItem.Builder<T>) {
        if (!this.builderMap.containsKey(clazz)) {
            this.builderMap[clazz] = builder
        }
    }

    open fun build(uiGameContext: BUiGameContext, item: T): BUiItem<T> {
        val builder = this.builderMap[item::class.java]!!
        val holder = builder.build(uiGameContext, item)
        holder.onDraw(uiGameContext)
        uiGameContext.gameContext.storage.putObject(holder)
        return holder
    }
}