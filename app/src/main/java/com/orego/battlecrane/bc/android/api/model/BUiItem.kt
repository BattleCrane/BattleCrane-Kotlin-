package com.orego.battlecrane.bc.android.api.model

import android.view.View
import com.orego.battlecrane.bc.android.api.asset.BUiAssets
import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode

abstract class BUiItem protected constructor(private val uiGameContext: BUiGameContext) {

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

    open var viewMode = BUiAssets.ViewMode.NEUTRAL

    abstract fun onDraw(uiGameContext: BUiGameContext)

    abstract fun getItemPath(): String

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

    open class UiClickMode(private val uiGameContext: BUiGameContext, open val item: BUiItem) : BUiClickMode {

        override fun onStartClickMode() {
            this.item.setDescription(this.uiGameContext)
        }
    }

    /**
     * Creates a unit.
     */

    abstract class Builder {

        protected open val configurations = mutableListOf<(BUiGameContext, BUiItem) -> Unit>()

        protected abstract fun onCreate(uiGameContext: BUiGameContext): BUiItem

        open fun build(uiGameContext: BUiGameContext): BUiItem {
            val uiItem = this.onCreate(uiGameContext)
            this.configurations.forEach { configure -> configure(uiGameContext, uiItem) }
            return uiItem
        }

        fun addConfiguration(configure : (BUiGameContext, BUiItem) -> Unit) {
            this.configurations += configure
        }

        fun removeConfiguration(configure : (BUiGameContext, BUiItem) -> Unit) {
            this.configurations -= configure
        }
    }
}