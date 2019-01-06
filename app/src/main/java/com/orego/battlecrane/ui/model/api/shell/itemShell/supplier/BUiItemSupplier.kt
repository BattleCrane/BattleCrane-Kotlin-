package com.orego.battlecrane.ui.model.api.shell.itemShell.supplier

import com.orego.battlecrane.ui.fragment.battle.BBattleFragment

interface BUiItemSupplier<T : Any> {

    val itemClassName: String

    fun supply(context: BBattleFragment.Presenter.BUiGameContext, item: T)
}