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
    }

    protected open lateinit var view: ConstraintLayout

    /**
     * Click mode.
     */

    protected val uiClickMode by lazy {
        UiClickMode(this.uiGameContext, this)
    }

    /**
     * View mode.
     */

    protected lateinit var viewMode: BUiAssets.ViewMode

    /**
     * Create.
     */

    override fun onCreateView(uiGameContext: BUiGameContext): View {
        val applicationContext = uiGameContext.uiProvider.applicationContext
        //Get command constraint layout:
        val constraintLayout = this.uiGameContext.uiProvider.commandConstraintLayout
        val childCount = constraintLayout.childCount
        val constraintLayoutId = constraintLayout.id
        val cellSize = constraintLayout.measuredWidth / COLUMN_COUNT
        //Create image view:
        val x = childCount % 2
        val y = childCount / 2
        val constraintParams = ConstraintLayout.LayoutParams(cellSize, cellSize)
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

    override fun onUpdateView(uiGameContext: BUiGameContext) {
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

    override fun onDestroyView(uiGameContext: BUiGameContext) {
        uiGameContext.uiProvider.commandConstraintLayout.removeView(this.view)
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
        if (!this.isActive() && !this.isSelected()) {
            this.viewMode = BUiAssets.ViewMode.ACTIVE
            this.onUpdateView(uiGameContext)
        }
    }

    fun isActive() = this.viewMode == BUiAssets.ViewMode.ACTIVE

    /**
     * Release.
     */

    fun release(uiGameContext: BUiGameContext) {
        if (!this.isReleased()) {
            this.viewMode = BUiAssets.ViewMode.LOCKED
            this.onUpdateView(uiGameContext)
        }
    }

    fun isReleased() = !this.isActive() && !this.isSelected()


    /**
     * Click mode.
     */

    open class UiClickMode(protected val uiGameContext: BUiGameContext, val action: BUiAction) : BUiClickMode {

        override fun onStartClickMode() {
            this.onShowDescription(this.uiGameContext)
        }

        /**
         * Draws description when unit is clicked.
         */

        open fun onShowDescription(uiGameContext: BUiGameContext) {}
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