package com.orego.battlecrane.bc.android.api.model.action

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.BUiItem
import com.orego.battlecrane.ui.util.setImageByAssets


abstract class BUiAction(private val uiGameContext: BUiGameContext) : BUiItem() {

    companion object {

        private const val COLUMN_COUNT = 2

        //TODO: DISTANCE_COEFFICIENT = 0.9
        private const val DISTANCE_COEFFICIENT = 0.9

    }

    protected open var view: ConstraintLayout? = null

    /**
     * Click mode.
     */

    protected open val uiClickMode by lazy {
        UiClickMode(this.uiGameContext, this)
    }

    /**
     * View mode.
     */

    protected var viewMode: BUiAssets.ViewMode = BUiAssets.ViewMode.ACTIVE

    abstract fun onPerform(uiGameContext: BUiGameContext)

    /**
     * Create.
     */

    override fun createView(uiGameContext: BUiGameContext): View {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        //Get command constraint layout:
        val constraintLayout = this.uiGameContext.uiProvider.commandConstraintLayout
        val childCount = constraintLayout.childCount
        val constraintLayoutId = constraintLayout.id
        val cellSize = constraintLayout.measuredWidth / COLUMN_COUNT
        val fixedCellSize = (cellSize * DISTANCE_COEFFICIENT).toInt()
        //Create image view:
        val x = childCount % 2
        val y = childCount / 2
        val constraintParams =
            ConstraintLayout.LayoutParams(fixedCellSize, fixedCellSize)
                .also {
                    it.startToStart = constraintLayoutId
                    it.topToTop = constraintLayoutId
                    it.marginStart = cellSize * x
                    it.topMargin = cellSize * y
                }
        val imagePath = this.createPath()
        val imageStream = applicationContext.assets.open(imagePath)
        val drawable = Drawable.createFromStream(imageStream, null)
        val actionLayout = ConstraintLayout(applicationContext)
            .also {
                it.id = View.generateViewId()
                it.background = drawable
            }
            .also {
                it.setOnClickListener {
                    uiGameContext.uiClickController.forcePushClickMode(this.uiClickMode)
                }
            }
        actionLayout.layoutParams = constraintParams
        this.view = actionLayout
        return actionLayout
    }

    /**
     * Update.
     */

    override fun updateView(uiGameContext: BUiGameContext) {
        val view = this.view
        if (view is ImageView) {
            val applicationContext = uiGameContext.uiProvider.applicationContext
            val path = this.createPath()
            view.setImageByAssets(applicationContext, path)
        }
    }

    protected abstract fun createPath(): String

    /**
     * Destroy.
     */

    override fun destroyView(uiGameContext: BUiGameContext) {
        uiGameContext.uiProvider.commandConstraintLayout.removeView(this.view)
    }

    abstract fun canActivate(uiGameContext: BUiGameContext): Boolean

    /**
     * Select.
     */

    fun select(uiGameContext: BUiGameContext) {
        if (!this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.SELECTED
            this.updateView(uiGameContext)
            this.showDescription(uiGameContext)
            this.onSelect(uiGameContext)
        }
    }

    fun isSelected() = this.viewMode == BUiAssets.ViewMode.SELECTED

    abstract fun showDescription(uiGameContext: BUiGameContext)

    abstract fun onSelect(uiGameContext: BUiGameContext)

    /**
     * Activate.
     */

    fun activate(uiGameContext: BUiGameContext) {
        if (!this.isActive() && !this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.ACTIVE
            this.updateView(uiGameContext)
        }
    }

    fun isActive() = this.viewMode == BUiAssets.ViewMode.ACTIVE

    /**
     * Release.
     */

    fun dismiss(uiGameContext: BUiGameContext) {
        if (!this.isDismissed()) {
            this.viewMode = BUiAssets.ViewMode.LOCKED
            this.updateView(uiGameContext)
        }
    }

    fun isDismissed() = !this.isActive() && !this.isSelected()

    /**
     * Click mode.
     */

    open class UiClickMode(protected val uiGameContext: BUiGameContext, open val action: BUiAction) : BUiClickMode {

        override fun onStartClickMode() {
            this.action.select(this.uiGameContext)
        }

        override fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
            if (nextUiClickMode is BUiAction.UiClickMode) {
                this.action.dismiss(this.uiGameContext)
                nextUiClickMode.onStartClickMode()
                return nextUiClickMode
            }
            return this
        }
    }

    /**
     * Builder.
     */

    abstract class Builder : BUiItem.Builder() {

        override fun build(uiGameContext: BUiGameContext): BUiAction {
            return super.build(uiGameContext) as BUiAction
        }
    }
}