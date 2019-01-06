package com.orego.battlecrane.ui.model.api.uiGameContext.clickController

import com.orego.battlecrane.ui.model.api.component.context.BUiContextComponent

@BUiContextComponent
class BClickController {

    private var currentClickMode: BClickMode? = null

    fun changeClickMode(clickMode: BClickMode): Boolean {
        val isEmptyMode = this.currentClickMode == null
        if (isEmptyMode) {
            this.currentClickMode = clickMode
        } else {
            if (this.currentClickMode!!.finish(clickMode)) {
                this.currentClickMode = null
            }
        }
        return isEmptyMode
    }

    fun forceChangeClickMode(clickMode: BClickMode) {
        this.currentClickMode = clickMode
    }
}