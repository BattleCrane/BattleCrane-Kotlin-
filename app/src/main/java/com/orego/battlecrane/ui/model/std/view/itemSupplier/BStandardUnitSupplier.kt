package com.orego.battlecrane.ui.model.std.view.itemSupplier

import android.widget.ImageView
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.model.api.shell.item.supplier.BUiItemSupplier
import com.orego.battlecrane.ui.util.byAssets

abstract class BStandardUnitSupplier : BUiItemSupplier<BUnit>{

    override val itemClassName: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    protected abstract val path : String

    override fun supply(uiContext: BBattleFragment.Presenter.BUiGameContext, item: BUnit) {
        val applicationContext = uiContext.applicationContext
        val surface = uiContext.mapSurface
        val map = surface.layout
        val dimensionX = map.measuredWidth / BMapController.MAP_SIZE
        val dimensionY = map.measuredHeight / BMapController.MAP_SIZE
        val imageView = ImageView(applicationContext).byAssets(applicationContext, dimensionX, dimensionY, this.path)
        surface.addView(imageView, dimensionX * item.x, dimensionY * item.y)
    }
}