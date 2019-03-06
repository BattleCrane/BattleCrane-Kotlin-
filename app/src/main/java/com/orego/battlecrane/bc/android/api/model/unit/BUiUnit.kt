package com.orego.battlecrane.bc.android.api.model.unit

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.BUiItem
import com.orego.battlecrane.bc.android.api.model.action.BUiAction
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.ui.util.setImageByAssets

abstract class BUiUnit(uiGameContext: BUiGameContext, open val unit: BUnit) : BUiItem() {

    val uiUnitId: Long

    /**
     * View on map.
     */

    protected var view: ImageView? = null

    /**
     * Handles unit click modes.
     */

    protected open val uiClickMode by lazy {
        UiClickMode(uiGameContext, this)
    }

    /**
     * View mode.
     */

    protected var viewMode = BUiAssets.ViewMode.SELECTED

    /**
     * Command list.
     */

    val actionMap = mutableMapOf<Class<out BUiAction>, BUiAction>()

    init {
        val contextGenerator = uiGameContext.gameContext.contextGenerator
        this.uiUnitId = contextGenerator.getIdGenerator(BUiUnit::class.java).generateId()
    }

    /**
     * Create.
     */

    override fun createView(uiGameContext: BUiGameContext): View {
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
                it.setOnClickListener { this.onClickView(uiGameContext) }
            }
        this.view = imageView
        this.dismiss(uiGameContext)
        return imageView
    }

    /**
     * Update.
     */

    override fun updateView(uiGameContext: BUiGameContext) {
        this.checkViewMode()
        this.updateImageView(uiGameContext)
    }

    private fun checkViewMode() {
        if (this.isDismissed()) {
            val playerId = this.unit.playerId
            val actualViewMode = BUiAssets.ViewMode.getByPlayerId(playerId)
            if (this.viewMode != actualViewMode) {
                this.viewMode = actualViewMode
            }
        }
    }

    private fun updateImageView(uiGameContext: BUiGameContext) {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        val path = this.createPath()
        this.view?.setImageByAssets(applicationContext, path)
    }

    protected abstract fun createPath(): String

    /**
     * Destroy.
     */

    override fun destroyView(uiGameContext: BUiGameContext) {
        uiGameContext.uiProvider.mapConstraintLayout.removeView(this.view)
    }

    /**
     * Handles when unit clicked.
     */

    protected open fun onClickView(uiGameContext: BUiGameContext) {
        uiGameContext.uiClickController.pushClickMode(this.uiClickMode)
    }

    /**
     * Select.
     */

    fun select(uiGameContext: BUiGameContext) {
        if (!this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.SELECTED
            this.updateView(uiGameContext)
            this.showDescription(uiGameContext)
            this.showCommands(uiGameContext)
        }
    }

    fun isSelected() = this.viewMode == BUiAssets.ViewMode.SELECTED

    open fun showDescription(uiGameContext: BUiGameContext) {}

    open fun showCommands(uiGameContext: BUiGameContext) {
        val commandConstraintLayout = uiGameContext.uiProvider.commandConstraintLayout
        val actions = this.actionMap.values
        actions.forEach { action ->
            val view = action.createView(uiGameContext)
            commandConstraintLayout.addView(view)
            if (action.canActivate(uiGameContext)) {
                action.activate(uiGameContext)
            }
        }
    }

    /**
     * Activate.
     */

    fun activate(uiGameContext: BUiGameContext) {
        if (!this.isDismissed()) {
            this.viewMode = BUiAssets.ViewMode.ACTIVE
            this.updateView(uiGameContext)
        }
    }

    fun isActive() = this.viewMode == BUiAssets.ViewMode.ACTIVE

    /**
     * Release.
     */

    fun dismiss(uiGameContext: BUiGameContext) {
        if (this.isActive() || this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.getByPlayerId(this.unit.playerId)
            this.updateView(uiGameContext)
            this.hideDescription(uiGameContext)
            this.hideCommands(uiGameContext)
        }
    }

    fun isDismissed() = !this.isActive() && !this.isSelected()

    open fun hideDescription(uiGameContext: BUiGameContext) {

    }

    open fun hideCommands(uiGameContext: BUiGameContext) {
        val actions = this.actionMap.values
        actions.forEach { action ->
            action.destroyView(uiGameContext)
        }
    }

    /**
     * Click mode.
     */

    class UiClickMode(val uiGameContext: BUiGameContext, val uiUnit: BUiUnit) : BUiClickMode {

        override fun onStartClickMode() {
            this.uiUnit.select(this.uiGameContext)
        }

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            val actionMap = this.uiUnit.actionMap
            if (nextUiClickMode !is BUiAction.UiClickMode
                || !actionMap.containsValue(nextUiClickMode.action)
            ) {
                this.uiUnit.dismiss(this.uiGameContext)
            }
            nextUiClickMode?.onStartClickMode()
            return nextUiClickMode
        }
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