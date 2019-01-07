package com.orego.battlecrane.ui.model.api.shell.item.supplier

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.BBattleFragment

abstract class BStandardUnitSupplier : BUiItemSupplier<BUnit> {

    protected abstract val path: String

    override fun supply(uiContext: BBattleFragment.Presenter.BUiGameContext, item: BUnit) {
        val applicationContext = uiContext.applicationContext
        val constraintLayout = uiContext.mapConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val cellSizeX = constraintLayout.measuredWidth / BMapController.MAP_SIZE
        val cellSizeY = constraintLayout.measuredHeight / BMapController.MAP_SIZE
        //Create params:
        val constraintParams = ConstraintLayout.LayoutParams(cellSizeX, cellSizeY)
            .also {
                it.startToStart = constraintLayoutId
                it.topToTop = constraintLayoutId
                it.marginStart = cellSizeX * item.x
                it.topMargin = cellSizeY * item.y
            }
        //Create image view:
        val imageView = ImageView(applicationContext)
            .also {
                val imageStream = applicationContext.assets.open(this.path)
                val drawable = Drawable.createFromStream(imageStream, null)
                it.id = View.generateViewId()
                it.setImageDrawable(drawable)
                it.layoutParams = constraintParams
            }
        constraintLayout.addView(imageView)
        this.supply(uiContext, item, imageView)
    }

    protected open fun supply(uiContext: BBattleFragment.Presenter.BUiGameContext, item: BUnit, imageView: ImageView) {
    }
}