package com.orego.battlecrane.bc.android.api.model.unit

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.BUiItem
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

abstract class BUiUnit(uiGameContext: BUiGameContext, open val unit: BUnit) : BUiItem(uiGameContext) {

    val uiUnitId: Long

    init {
        val contextGenerator = uiGameContext.gameContext.contextGenerator
        this.uiUnitId = contextGenerator.getIdGenerator(BUiUnit::class.java).generateId()
    }

    override fun onDraw(uiGameContext: BUiGameContext) {
        val uiProvider = uiGameContext.uiProvider
        val applicationContext = uiProvider.applicationContext
        val constraintLayout = uiProvider.mapConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val cellSizeX = constraintLayout.measuredWidth / BMapController.MAP_SIZE
        val cellSizeY = constraintLayout.measuredHeight / BMapController.MAP_SIZE
        //Create params:
        val constraintParams = ConstraintLayout.LayoutParams(
            this.unit.width * cellSizeX,
            this.unit.height * cellSizeY
        ).also {
            it.startToStart = constraintLayoutId
            it.topToTop = constraintLayoutId
            it.marginStart = cellSizeX * this.unit.x
            it.topMargin = cellSizeY * this.unit.y
        }
        //Create image unitView:
        val imageView = ImageView(applicationContext)
            .also {
                val imageStream = applicationContext.assets.open(this.getItemPath())
                val drawable = Drawable.createFromStream(imageStream, null)
                it.id = View.generateViewId()
                it.setImageDrawable(drawable)
                it.layoutParams = constraintParams
            }
            .also {
                it.setOnClickListener { this.onClick(uiGameContext) }
            }
        constraintLayout.addView(imageView)
        this.unitView = imageView
    }

    /**
     * Builder.
     */

    abstract class Builder(protected open val unit : BUnit) : BUiItem.Builder() {

        override fun build(uiGameContext: BUiGameContext): BUiUnit {
            return super.build(uiGameContext) as BUiUnit
        }
    }
}