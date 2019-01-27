package com.orego.battlecrane.bc.android.api.model.unit

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.BUiItem
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import org.intellij.lang.annotations.MagicConstant

abstract class BUiUnit(uiGameContext: BUiGameContext, unit: BUnit) : BUiItem<BUnit>(unit) {

    companion object {

        @MagicConstant
        private const val CELL_COEFFICIENT = 0.9
    }

    open val unitView by lazy {
        this.onDraw(uiGameContext)
    }

    val uiUnitId: Long

    init {
        val contextGenerator = uiGameContext.gameContext.contextGenerator
        this.uiUnitId = contextGenerator.getIdGenerator(BUiUnit::class.java).generateId()
    }

    abstract fun getItemPath(): String

    protected open fun onDraw(uiGameContext: BUiGameContext): View {
        val uiProvider = uiGameContext.uiProvider
        val applicationContext = uiProvider.applicationContext
        val constraintLayout = uiProvider.mapConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val cellSizeX = constraintLayout.measuredWidth / BMapController.MAP_SIZE
        val cellSizeY = constraintLayout.measuredHeight / BMapController.MAP_SIZE
        //Create params:
        val constraintParams = ConstraintLayout.LayoutParams(
            (this.item.width * cellSizeX * CELL_COEFFICIENT).toInt(),
            (this.item.height * cellSizeY * CELL_COEFFICIENT).toInt()
        ).also {
            it.startToStart = constraintLayoutId
            it.topToTop = constraintLayoutId
            it.marginStart = cellSizeX * item.x
            it.topMargin = cellSizeY * item.y
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
        return imageView
    }

    open fun showDescription(uiGameContext: BUiGameContext) {
    }

    open fun onClick(uiGameContext: BUiGameContext) {
        uiGameContext.uiClickController.pushClickMode(UiClickMode(uiGameContext, this))
    }

    /**
     * Click mode.
     */

    open class UiClickMode(private val uiGameContext: BUiGameContext, val unit: BUiUnit) : BUiClickMode {

        override fun onStartClickMode() {
            this.unit.showDescription(this.uiGameContext)
        }
    }

    /**
     * Factory.
     */

    open class Factory : BUiItem.BUiItemFactory<BUnit>()

    /**
     * Builder.
     */

    abstract class Builder : BUiItem.Builder<BUnit>()
}