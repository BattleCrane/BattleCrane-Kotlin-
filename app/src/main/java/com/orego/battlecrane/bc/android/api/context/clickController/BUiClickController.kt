package com.orego.battlecrane.bc.android.api.context.clickController

class BUiClickController {

    var currentUiClickMode: BUiClickMode? = null

    fun pushClickMode(nextUiClickMode: BUiClickMode?) {
        val currentClickMode = this.currentUiClickMode
        if (currentClickMode == null) {
            this.currentUiClickMode = nextUiClickMode
            nextUiClickMode?.onStartClickMode()
        } else {
            this.currentUiClickMode = currentClickMode.onNextClickMode(nextUiClickMode)
        }
    }

    fun forcePushClickMode(uiClickMode: BUiClickMode?) {
        this.currentUiClickMode = uiClickMode
        uiClickMode?.onStartClickMode()
    }
}