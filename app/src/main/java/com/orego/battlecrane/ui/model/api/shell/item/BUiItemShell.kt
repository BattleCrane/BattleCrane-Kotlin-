package com.orego.battlecrane.ui.model.api.shell.item

import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.model.api.shell.item.supplier.BUiItemSupplier

open class BUiItemShell<T : Any> {

    private val itemSupplierMap = mutableMapOf<String, BUiItemSupplier<T>>()

    fun addSupplier(itemSupplier: BUiItemSupplier<T>) {
        val type = itemSupplier.itemClassName
        if (!this.itemSupplierMap.containsKey(type)) {
            this.itemSupplierMap[type] = itemSupplier
        } else {
            throw IllegalStateException("The supplier itemClassName has added!")
        }
    }

    open fun provide(
        uiContext: BBattleFragment.Presenter.BUiGameContext, item: T, type: String
    ) = this.itemSupplierMap[type]?.supply(uiContext, item) ?: this.supplyByDefault(uiContext, item)

    open fun supplyByDefault(context: BBattleFragment.Presenter.BUiGameContext, item: T) {
        throw IllegalStateException("Not supported itemClassName!")
    }
}