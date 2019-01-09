package com.orego.battlecrane.ui.model.api.holder.unit

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.heap.BUnitHolderHeap
import com.orego.battlecrane.ui.model.api.holder.BHolder

abstract class BUnitHolder(gameContext: BGameContext, unit : BUnit) : BHolder<BUnit>(unit) {

    abstract val unitView : View

    val uiUnitId : Long = gameContext.contextGenerator.getIdGenerator(BUnitHolderHeap::class.java).generateId()

    companion object {

        fun createImageView(uiContext: BUiGameContext, item : BUnit, itemPath : String) : ImageView {
            val uiProvider = uiContext.uiProvider
            val applicationContext = uiProvider.applicationContext
            val constraintLayout = uiProvider.mapConstraintLayout
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
            //Create image unitView:
            val imageView = ImageView(applicationContext)
                .also {
                    val imageStream = applicationContext.assets.open(itemPath)
                    val drawable = Drawable.createFromStream(imageStream, null)
                    it.id = View.generateViewId()
                    it.setImageDrawable(drawable)
                    it.layoutParams = constraintParams
                }
            constraintLayout.addView(imageView)
            return imageView
        }
    }

    class Factory : BHolder.Factory<BUnit>()

    abstract class Builder : BHolder.Builder<BUnit>()
}