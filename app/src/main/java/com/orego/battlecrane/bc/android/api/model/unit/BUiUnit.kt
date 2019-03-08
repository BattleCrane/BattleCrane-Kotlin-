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

//TODO: GENERIC BY UNIT!

abstract class BUiUnit(uiGameContext: BUiGameContext, open val unit: BUnit) : BUiItem() {

    val uiUnitId: Long

    /**
     * View on map.
     */

    protected var view: ImageView? = null

    private var isShowedCommands = false

    /**
     * Handles unit click modes.
     */

    protected open val uiClickMode by lazy {
        UiClickMode(uiGameContext, this)
    }

    /**
     * View mode.
     */

    var viewMode = BUiAssets.ViewMode.SELECTED

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
        val androidContext = uiProvider.androidContext
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
        val imageView = ImageView(androidContext)
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
            this.viewMode = actualViewMode
        }
    }

    private fun updateImageView(uiGameContext: BUiGameContext) {
        val androidContext = uiGameContext.uiProvider.androidContext
        val path = this.getAssetPath()
        this.view?.setImageByAssets(androidContext, path)
    }

    protected abstract fun getAssetPath(): String

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
            this.onShowInfo(uiGameContext)
            this.showCommands(uiGameContext)
        }
    }

    fun isSelected() = this.viewMode == BUiAssets.ViewMode.SELECTED

    open fun onShowInfo(uiGameContext: BUiGameContext) {}

    open fun showCommands(uiGameContext: BUiGameContext) {
        if (!this.isShowedCommands) {
            this.createCommands(uiGameContext)
            this.checkCommands(uiGameContext)
            this.isShowedCommands = true
        }
    }

    fun createCommands(uiGameContext: BUiGameContext) {
        val commandConstraintLayout = uiGameContext.uiProvider.commandConstraintLayout
        val actions = this.actionMap.values
        actions.forEach { action ->
            val view = action.createView(uiGameContext)
            commandConstraintLayout.addView(view)
        }
    }

    fun checkCommands(uiGameContext: BUiGameContext) {
        this.actionMap.values.forEach { action ->
            if (action.canActivate(uiGameContext)) {
                action.activate(uiGameContext)
            } else {
                action.dismiss(uiGameContext)
            }
        }
    }

    /**
     * Activate.
     */

    fun activate(uiGameContext: BUiGameContext) {
        println("START TO ACTIVATE")
        if (this.isDismissed()) {
            println("ACTIVATE!")
            this.viewMode = BUiAssets.ViewMode.ACTIVE
            this.updateView(uiGameContext)
        }
    }

    fun isActive() = this.viewMode == BUiAssets.ViewMode.ACTIVE

    fun canActivate(uiGameContext: BUiGameContext): Boolean {
        val actions = this.actionMap.values
        actions.forEach { action ->
            if (action.canActivate(uiGameContext)) {
                return true
            }
        }
        return false
    }

    /**
     * Release.
     */

    fun dismiss(uiGameContext: BUiGameContext) {
        if (this.isActive() || this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.getByPlayerId(this.unit.playerId)
            this.updateView(uiGameContext)
            this.onHideInfo(uiGameContext)
            this.hideCommands(uiGameContext)
        }
    }

    fun isDismissed() = !this.isActive() && !this.isSelected()

    open fun onHideInfo(uiGameContext: BUiGameContext) {

    }

    open fun hideCommands(uiGameContext: BUiGameContext) {
        if (this.isShowedCommands) {
            val actions = this.actionMap.values
            actions.forEach { action ->
                action.destroyView(uiGameContext)
            }
            this.isShowedCommands = false
        }
    }

    /**
     * Click mode.
     */

    class UiClickMode(val uiGameContext: BUiGameContext, val uiUnit: BUiUnit) : BUiClickMode() {

        override fun onStartClickMode() {
            this.uiUnit.select(this.uiGameContext)
        }

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
//            nextUiClickMode?.previousMode = this
            val actionMap = this.uiUnit.actionMap
            if (nextUiClickMode !is BUiAction.UiClickMode
                || !actionMap.containsValue(nextUiClickMode.action)
            ) {
                if (this.uiUnit.canActivate(this.uiGameContext)) {
                    this.uiUnit.viewMode = BUiAssets.ViewMode.ACTIVE
                    this.uiUnit.updateView(this.uiGameContext)
                    this.uiUnit.onHideInfo(this.uiGameContext)
                    this.uiUnit.hideCommands(this.uiGameContext)
                } else {
                    this.uiUnit.dismiss(this.uiGameContext)
                }
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