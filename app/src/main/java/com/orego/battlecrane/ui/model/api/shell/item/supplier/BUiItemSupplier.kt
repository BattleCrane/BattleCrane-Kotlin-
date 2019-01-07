package com.orego.battlecrane.ui.model.api.shell.item.supplier

import com.orego.battlecrane.ui.fragment.battle.BBattleFragment

interface BUiItemSupplier<T : Any> {

    val itemClassName: String

    fun supply(uiContext: BBattleFragment.Presenter.BUiGameContext, item: T)
}