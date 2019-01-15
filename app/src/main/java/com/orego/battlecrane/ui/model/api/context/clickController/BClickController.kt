package com.orego.battlecrane.ui.model.api.context.clickController

class BClickController {

    private var currentClickMode: BClickMode? = null

    fun changeClickMode(nextClickMode: BClickMode): Boolean {
        val isEmptyMode = this.currentClickMode == null
        if (isEmptyMode) {
            this.currentClickMode = nextClickMode
        } else {
            if (this.currentClickMode!!.finish(nextClickMode)) {
                this.currentClickMode = null
            }
        }
        return isEmptyMode
    }

    fun forceChangeClickMode(clickMode: BClickMode) {
        this.currentClickMode = clickMode
    }
}