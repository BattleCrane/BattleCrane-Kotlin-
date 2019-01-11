package com.orego.battlecrane.ui.model.api.holder

import com.orego.battlecrane.ui.model.api.context.BUiGameContext

abstract class BHolder<T>(open val item : T) {

    open class Factory<T : Any> {

        private val builderMap : MutableMap<Class<out T>, Builder<T>> = mutableMapOf()

        fun addBuilder(entry : Map.Entry<Class<out T>, Builder<T>>) {
            this.builderMap[entry.key] = entry.value
        }

        fun addBuilder(clazz: Class<out T>, builder: Builder<T>){
            this.builderMap[clazz] =  builder
        }

        fun build(uiGameContext: BUiGameContext, item : T) : BHolder<T> {
            val builder = this.builderMap[item::class.java]!!
            return builder.build(uiGameContext, item)
        }
    }

    abstract class Builder<T> {

        abstract fun build(uiGameContext: BUiGameContext, item : T) : BHolder<T>
    }
}