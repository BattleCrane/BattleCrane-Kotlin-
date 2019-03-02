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

abstract class BUiUnit(uiGameContext: BUiGameContext, unit: BUnit) : BUiItem<BUnit>(unit) {

    val uiUnitId: Long

    /**
     * View on map.
     */

    open lateinit var unitView: View

    /**
     * Handles unit click modes.
     */

    protected val uiClickMode by lazy {
        UiClickMode(uiGameContext, this)
    }

    /**
     * Type of view mode.
     */

    var viewType = ViewMode.NORMAL

    init {
        val contextGenerator = uiGameContext.gameContext.contextGenerator
        this.uiUnitId = contextGenerator.getIdGenerator(BUiUnit::class.java).generateId()
    }

    abstract fun getItemPath(): String

    override fun onDraw(uiGameContext: BUiGameContext) {
        val uiProvider = uiGameContext.uiProvider
        val applicationContext = uiProvider.applicationContext
        val constraintLayout = uiProvider.mapConstraintLayout
        val constraintLayoutId = constraintLayout.id
        val cellSizeX = constraintLayout.measuredWidth / BMapController.MAP_SIZE
        val cellSizeY = constraintLayout.measuredHeight / BMapController.MAP_SIZE
        //Create params:
        val constraintParams = ConstraintLayout.LayoutParams(
            this.item.width * cellSizeX,
            this.item.height * cellSizeY
        ).also {
            it.startToStart = constraintLayoutId
            it.topToTop = constraintLayoutId
            it.marginStart = cellSizeX * this.item.x
            it.topMargin = cellSizeY * this.item.y
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
     * Draws description when unit is clicked.
     */

    open fun setDescription(uiGameContext: BUiGameContext) {
    }

    /**
     * Handles when unit clicked.
     */

    open fun onClick(uiGameContext: BUiGameContext) {
        uiGameContext.uiClickController.pushClickMode(this.uiClickMode)
    }

    /**
     * Click mode.
     */

    open class UiClickMode(private val uiGameContext: BUiGameContext, val unit: BUiUnit) : BUiClickMode {

        override fun onStartClickMode() {
            this.unit.setDescription(this.uiGameContext)
        }
    }

    /**
     * Builder.
     */

    abstract class Builder : BUiItem.Builder<BUnit>()

    /**
     * View mode shows current mode of unit interface. For example: unit selected, active e.t.c..
     */

    enum class ViewMode {
        NORMAL, ACTIVE, SELECTED
    }
}