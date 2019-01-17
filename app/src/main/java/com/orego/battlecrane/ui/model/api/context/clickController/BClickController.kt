package com.orego.battlecrane.ui.model.api.context.clickController

class BClickController {

    var currentClickMode: BClickMode? = null

    fun pushClickMode(nextClickMode: BClickMode) {
        if (this.currentClickMode == null) {
            this.currentClickMode = nextClickMode
        } else {
            this.currentClickMode = this.currentClickMode!!.handle(nextClickMode)
        }
    }

    fun forcePushClickMode(clickMode: BClickMode) {
        this.currentClickMode = clickMode
    }
}