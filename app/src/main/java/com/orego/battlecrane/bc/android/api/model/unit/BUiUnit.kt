package com.orego.battlecrane.bc.android.api.model.unit

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.BUiItem
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.ui.util.setImageByAssets

abstract class BUiUnit(uiGameContext: BUiGameContext, open val unit: BUnit) : BUiItem() {

    val uiUnitId: Long

    /**
     * View on map.
     */

    protected lateinit var view: ImageView

    /**
     * Handles unit click modes.
     */

    protected val uiClickMode by lazy {
        UiClickMode(uiGameContext, this)
    }

    /**
     * View mode.
     */

    protected lateinit var viewMode: BUiAssets.ViewMode

    init {
        val contextGenerator = uiGameContext.gameContext.contextGenerator
        this.uiUnitId = contextGenerator.getIdGenerator(BUiUnit::class.java).generateId()
    }

    /**
     * Create.
     */

    override fun onCreateView(uiGameContext: BUiGameContext): View {
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
        )
            .also {
                it.startToStart = constraintLayoutId
                it.topToTop = constraintLayoutId
                it.marginStart = cellSizeX * this.unit.x
                it.topMargin = cellSizeY * this.unit.y
            }
        //Create image view:
        val imageView = ImageView(applicationContext)
            .also {
                it.id = View.generateViewId()
                it.layoutParams = constraintParams
            }
            .also {
                it.setOnClickListener { this.onClick(uiGameContext) }
            }
        this.view = imageView
        this.release(uiGameContext)
        return imageView
    }

    /**
     * Update.
     */

    override fun onUpdateView(uiGameContext: BUiGameContext) {
        this.checkViewMode()
        this.updateView(uiGameContext)
    }

    private fun checkViewMode() {
        if (this.isReleased()) {
            val playerId = this.unit.playerId
            val actualViewMode = BUiAssets.ViewMode.getByPlayerId(playerId)
            if (this.viewMode != actualViewMode) {
                this.viewMode = actualViewMode
            }
        }
    }

    private fun updateView(uiGameContext: BUiGameContext) {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        val path = this.createPath()
        this.view.setImageByAssets(applicationContext, path)
    }

    protected abstract fun createPath(): String

    /**
     * Destroy.
     */

    override fun onDestroyView(uiGameContext: BUiGameContext) {
        uiGameContext.uiProvider.mapConstraintLayout.removeView(this.view)
    }

    /**
     * Handles when unit clicked.
     */

    private fun onClick(uiGameContext: BUiGameContext) {
        uiGameContext.uiClickController.pushClickMode(this.uiClickMode)
    }

    /**
     * Select.
     */

    fun select(uiGameContext: BUiGameContext) {
        if (!this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.SELECTED
            this.onUpdateView(uiGameContext)
        }
    }

    fun isSelected() = this.viewMode == BUiAssets.ViewMode.SELECTED

    /**
     * Activate.
     */

    fun activate(uiGameContext: BUiGameContext) {
        if (!this.isReleased()) {
            this.viewMode = BUiAssets.ViewMode.ACTIVE
            this.onUpdateView(uiGameContext)
        }
    }

    fun isActive() = this.viewMode == BUiAssets.ViewMode.ACTIVE

    /**
     * Release.
     */

    fun release(uiGameContext: BUiGameContext) {
        if (this.isActive() || this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.getByPlayerId(this.unit.playerId)
            this.onUpdateView(uiGameContext)
        }
    }

    fun isReleased() = !this.isActive() && !this.isSelected()

    /**
     * Click mode.
     */

    open class UiClickMode(protected val uiGameContext: BUiGameContext, open val item: BUiItem) : BUiClickMode {

        override fun onStartClickMode() {
            this.onShowDescription(this.uiGameContext)
            this.onShowCommands(this.uiGameContext)
        }

        /**
         * Draws description when unit is clicked.
         */

        open fun onShowDescription(uiGameContext: BUiGameContext) {}

        open fun onShowCommands(uiGameContext: BUiGameContext) {}
    }

    /**
     * Builder.
     */

    abstract class Builder(protected open val unit: BUnit) : BUiItem.Builder() {

        override fun build(uiGameContext: BUiGameContext): BUiUnit {
            return super.build(uiGameContext) as BUiUnit
        }
    }
}