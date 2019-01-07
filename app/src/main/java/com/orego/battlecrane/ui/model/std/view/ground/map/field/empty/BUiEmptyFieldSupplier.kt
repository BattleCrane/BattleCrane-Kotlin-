package com.orego.battlecrane.ui.model.std.view.ground.map.field.empty

import android.widget.ImageView
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment
import com.orego.battlecrane.ui.model.api.shell.item.supplier.BUiItemSupplier
import com.orego.battlecrane.ui.util.byAssets

class BUiEmptyFieldSupplier : BUiItemSupplier<BUnit>{

    companion object {

        private const val PATH = "std/grass/unit/empty_field.png"
    }

    override val itemClassName: String = BEmptyField::class.java.name

    override fun supply(uiContext: BBattleFragment.Presenter.BUiGameContext, item: BUnit) {
        val applicationContext = uiContext.applicationContext
        val surface = uiContext.mapSurface
        val map = surface.layout
        val dimensionX = map.measuredWidth / BMapController.MAP_SIZE
        val dimensionY = map.measuredHeight / BMapController.MAP_SIZE
        val imageView = ImageView(applicationContext).byAssets(applicationContext, dimensionX, dimensionY, PATH)
        surface.addView(imageView, dimensionX * item.x, dimensionY * item.y)
    }
}