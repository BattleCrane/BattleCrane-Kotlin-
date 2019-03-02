package com.orego.battlecrane.bc.android.api.model

import com.orego.battlecrane.bc.android.api.context.BUiGameContext

abstract class BUiItem<T> protected constructor(open val item: T) {

    open fun onDraw(uiGameContext: BUiGameContext) {
    }

    /**
     * Creates a item.
     */

    abstract class Builder<T> {

        protected open val configurations = mutableListOf<(BUiGameContext, BUiItem<T>) -> Unit>()

        protected abstract fun onCreate(uiGameContext: BUiGameContext, item: T): BUiItem<T>

        fun build(uiGameContext: BUiGameContext, item: T): BUiItem<T> {
            val uiItem = this.onCreate(uiGameContext, item)
            this.configurations.forEach { configure -> configure(uiGameContext, uiItem) }
            return uiItem
        }

        fun addConfiguration(configure : (BUiGameContext, BUiItem<T>) -> Unit) {
            this.configurations += configure
        }


        fun removeConfiguration(configure : (BUiGameContext, BUiItem<T>) -> Unit) {
            this.configurations -= configure
        }
    }
}