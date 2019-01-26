package com.orego.battlecrane.bc.android.api.context.clickController

class BUiClickController {

    var currentUiUiClickMode: BUiClickMode? = null

    fun pushClickMode(nextUiClickMode: BUiClickMode) {
        if (this.currentUiUiClickMode == null) {
            this.currentUiUiClickMode = nextUiClickMode
            nextUiClickMode.onStartClickMode()
        } else {
            this.currentUiUiClickMode = this.currentUiUiClickMode!!.onNextClickMode(nextUiClickMode)
        }
    }

    fun forcePushClickMode(uiClickMode: BUiClickMode) {
        this.currentUiUiClickMode = uiClickMode
    }
}